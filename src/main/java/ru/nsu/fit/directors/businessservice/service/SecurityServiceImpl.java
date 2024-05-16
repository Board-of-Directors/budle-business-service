package ru.nsu.fit.directors.businessservice.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.exceptions.UserNotLoggedInException;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.repository.BusinessUserRepository;
import ru.nsu.fit.directors.businessservice.security.JwtTokenRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityServiceImpl implements SecurityService {
    private final BusinessUserRepository businessUserRepository;
    private final JwtTokenRepository jwtTokenRepository;

    @Nonnull
    @Override
    public BusinessUser getLoggedInUser() {
        return businessUserRepository.findById(jwtTokenRepository.getUserIdOrThrow())
            .orElseThrow(UserNotLoggedInException::new);
    }
}
