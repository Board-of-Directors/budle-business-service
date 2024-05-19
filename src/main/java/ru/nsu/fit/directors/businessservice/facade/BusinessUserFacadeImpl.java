package ru.nsu.fit.directors.businessservice.facade;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.dto.ChangeBusinessUserRequest;
import ru.nsu.fit.directors.businessservice.dto.request.BusinessUserRegisterRequest;
import ru.nsu.fit.directors.businessservice.dto.response.BusinessUserCredentialsResponse;
import ru.nsu.fit.directors.businessservice.mapper.BusinessUserMapper;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.security.JwtTokenRepository;
import ru.nsu.fit.directors.businessservice.service.BusinessUserService;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class BusinessUserFacadeImpl implements BusinessUserFacade {
    private final BusinessUserService businessUserService;
    private final JwtTokenRepository jwtTokenRepository;
    private final BusinessUserMapper businessUserMapper;

    @Override
    public void register(BusinessUserRegisterRequest businessUserRegisterRequest) {
        businessUserService.registerBusinessUser(businessUserRegisterRequest);
    }

    @Nonnull
    @Override
    public BusinessUserCredentialsResponse getActiveUser() {
        Long businessUserId = jwtTokenRepository.getUserIdOrThrow();
        BusinessUser businessUser = businessUserService.getById(businessUserId);
        return businessUserMapper.toResponse(businessUser);
    }

    @Override
    public void changeBusinessUser(ChangeBusinessUserRequest changeBusinessUserRequest) {
        Long businessUserId = jwtTokenRepository.getUserIdOrThrow();
        BusinessUser businessUser = businessUserService.getById(businessUserId);
        businessUserMapper.updateModel(businessUser, changeBusinessUserRequest);
        businessUserService.save(businessUser);
    }
}
