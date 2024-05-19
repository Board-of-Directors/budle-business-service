package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.dto.request.BusinessUserRegisterRequest;
import ru.nsu.fit.directors.businessservice.exceptions.AlreadyRegisteredException;
import ru.nsu.fit.directors.businessservice.exceptions.EntityNotFoundException;
import ru.nsu.fit.directors.businessservice.exceptions.InvalidCredentialsException;
import ru.nsu.fit.directors.businessservice.mapper.BusinessUserMapper;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.EntityType;
import ru.nsu.fit.directors.businessservice.repository.BusinessUserRepository;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Service
@ParametersAreNonnullByDefault
@RequiredArgsConstructor
public class BusinessUserServiceImpl implements BusinessUserService {
    private final BusinessUserRepository businessUserRepository;
    private final BusinessUserMapper businessUserMapper;
    private final MailNotificationService notificationService;

    @Override
    public void registerBusinessUser(BusinessUserRegisterRequest businessUserRegisterRequest) {
        if (businessUserRepository.existsBusinessUserByEmailOrPhoneNumber(
            businessUserRegisterRequest.email(),
            businessUserRegisterRequest.phoneNumber()
        )) {
            throw new AlreadyRegisteredException();
        }
        String password = RandomStringUtils.random(12, true, true);
        BusinessUser businessUser = businessUserMapper.toBusinessUser(businessUserRegisterRequest, password);
        businessUserRepository.save(businessUser);
        notificationService.sendRegistrationNotification(businessUser, password);
    }

    @Nonnull
    @Override
    public BusinessUser getByLogin(String username) {
        return businessUserRepository.findBusinessUserByLogin(username)
            .orElseThrow(InvalidCredentialsException::new);
    }

    @Nonnull
    @Override
    public BusinessUser getById(Long businessUserId) {
        return businessUserRepository.findById(businessUserId)
            .orElseThrow(() -> new EntityNotFoundException(EntityType.BUSINESS_USER, businessUserId));
    }
}
