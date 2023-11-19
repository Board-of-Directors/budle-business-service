package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.dto.BusinessUserLoginRequest;
import ru.nsu.fit.directors.businessservice.dto.request.BusinessUserRegisterRequest;
import ru.nsu.fit.directors.businessservice.exceptions.AlreadyRegisteredException;
import ru.nsu.fit.directors.businessservice.exceptions.InvalidCredentialsException;
import ru.nsu.fit.directors.businessservice.mapper.BusinessUserMapper;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.repository.BusinessUserRepository;

import javax.annotation.ParametersAreNonnullByDefault;

@Service
@ParametersAreNonnullByDefault
@RequiredArgsConstructor
public class BusinessUserServiceImpl implements BusinessUserService, UserDetailsService {
    private final BusinessUserRepository businessUserRepository;
    private final BusinessUserMapper businessUserMapper;
    private final NotificationService notificationService;

    @Override
    public void registerBusinessUser(BusinessUserRegisterRequest businessUserRegisterRequest) {
        if (businessUserRepository.existsBusinessUserByEmailOrPhoneNumber(businessUserRegisterRequest.email(), businessUserRegisterRequest.phoneNumber())) {
            throw new AlreadyRegisteredException();
        }
        String password = RandomStringUtils.random(12, true, true);
        BusinessUser businessUser = businessUserMapper.toBusinessUser(businessUserRegisterRequest, password);
        businessUserRepository.save(businessUser);
        notificationService.sendRegistrationNotification(businessUser, password);
    }

    @Override
    public void loginBusinessUser(BusinessUserLoginRequest businessUserLoginRequest) {
        businessUserRepository.findBusinessUserByLogin(businessUserLoginRequest.login())
            .orElseThrow(InvalidCredentialsException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return businessUserRepository.findBusinessUserByLogin(username)
            .orElseThrow(InvalidCredentialsException::new);
    }
}
