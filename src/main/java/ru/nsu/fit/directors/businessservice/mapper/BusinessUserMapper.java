package ru.nsu.fit.directors.businessservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.dto.request.BusinessUserRegisterRequest;
import ru.nsu.fit.directors.businessservice.dto.request.RequestWorkerDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseWorkerDto;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;

@Component
@RequiredArgsConstructor
public class BusinessUserMapper {
    private final PasswordEncoder passwordEncoder;

    public BusinessUser toBusinessUser(BusinessUserRegisterRequest registerRequest, String password) {
        String[] names = registerRequest.name().split(" ");
        String login = registerRequest.email().split("@")[0];
        return new BusinessUser()
            .setMiddleName(names[0])
            .setFirstName(names[1])
            .setLastName(names[2])
            .setEmail(registerRequest.email())
            .setPhoneNumber(registerRequest.phoneNumber())
            .setPassword(passwordEncoder.encode(password))
            .setLogin(login);

    }

    public BusinessUser toBusinessUser(RequestWorkerDto workerDto, String password) {
        String[] names = workerDto.name().split(" ");
        String login = workerDto.email().split("@")[0];
        return new BusinessUser()
            .setMiddleName(names[0])
            .setFirstName(names[1])
            .setLastName(names[2])
            .setEmail(workerDto.email())
            .setPassword(passwordEncoder.encode(password))
            .setLogin(login);
    }

    public ResponseWorkerDto toWorkerDto(BusinessUser businessUser) {
        return ResponseWorkerDto.builder()
            .id(businessUser.getId())
            .firstName(businessUser.getFirstName())
            .middleName(businessUser.getMiddleName())
            .build();
    }
}
