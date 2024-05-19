package ru.nsu.fit.directors.businessservice.mapper;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.dto.ChangeBusinessUserRequest;
import ru.nsu.fit.directors.businessservice.dto.request.BusinessUserRegisterRequest;
import ru.nsu.fit.directors.businessservice.dto.request.RequestWorkerDto;
import ru.nsu.fit.directors.businessservice.dto.response.BusinessUserCredentialsResponse;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseWorkerDto;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class BusinessUserMapper {
    private final PasswordEncoder passwordEncoder;

    @Nonnull
    public BusinessUser toBusinessUser(BusinessUserRegisterRequest registerRequest, String password) {
        String login = registerRequest.email().split("@")[0];
        return new BusinessUser()
            .setFullName(registerRequest.name())
            .setEmail(registerRequest.email())
            .setPhoneNumber(registerRequest.phoneNumber())
            .setPassword(passwordEncoder.encode(password))
            .setLogin(login);

    }

    @Nonnull
    public BusinessUser toBusinessUser(RequestWorkerDto workerDto, String password) {
        String login = workerDto.email().split("@")[0];
        return new BusinessUser()
            .setFullName(workerDto.name())
            .setEmail(workerDto.email())
            .setPassword(passwordEncoder.encode(password))
            .setLogin(login);
    }

    @Nonnull
    public ResponseWorkerDto toWorkerDto(BusinessUser businessUser) {
        return ResponseWorkerDto.builder()
            .id(businessUser.getId())
            .firstName(businessUser.getFirstName())
            .middleName(businessUser.getMiddleName())
            .lastName(businessUser.getLastName())
            .build();
    }

    @Nonnull
    public BusinessUserCredentialsResponse toResponse(BusinessUser businessUser) {
        return BusinessUserCredentialsResponse.builder()
            .id(businessUser.getId())
            .firstName(businessUser.getFirstName())
            .middleName(businessUser.getMiddleName())
            .lastName(businessUser.getLastName())
            .email(businessUser.getEmail())
            .phoneNumber(businessUser.getPhoneNumber())
            .login(businessUser.getLogin())
            .build();
    }

    public void updateModel(BusinessUser businessUser, ChangeBusinessUserRequest changeBusinessUserRequest) {
        Optional.ofNullable(changeBusinessUserRequest.email()).ifPresent(businessUser::setEmail);
        Optional.ofNullable(changeBusinessUserRequest.phoneNumber()).ifPresent(businessUser::setPhoneNumber);
        Optional.ofNullable(changeBusinessUserRequest.fullName()).ifPresent(businessUser::setFullName);
    }
}
