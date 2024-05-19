package ru.nsu.fit.directors.businessservice.facade;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.dto.AuthResponse;
import ru.nsu.fit.directors.businessservice.dto.BusinessUserLoginRequest;
import ru.nsu.fit.directors.businessservice.exceptions.UnauthorizedException;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Session;
import ru.nsu.fit.directors.businessservice.security.JwtTokenProvider;
import ru.nsu.fit.directors.businessservice.security.claims.RefreshTokenClaims;
import ru.nsu.fit.directors.businessservice.security.model.RefreshToken;
import ru.nsu.fit.directors.businessservice.service.BusinessUserService;
import ru.nsu.fit.directors.businessservice.service.SessionService;

@Slf4j
@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class UserFacadeImpl implements UserFacade {
    private final BusinessUserService businessUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SessionService sessionService;

    @Nonnull
    @Override
    public AuthResponse loginCredentials(BusinessUserLoginRequest userCredentials) {
        BusinessUser user = businessUserService.getByLogin(userCredentials.login());
        if (!passwordEncoder.matches(userCredentials.password(), user.getPassword())) {
            throw new UnauthorizedException("Не вошел в аккаунт");
        }
        RefreshToken refreshToken = jwtTokenProvider.createRefreshToken(userCredentials.login());
        sessionService.createSession(user, refreshToken);
        return new AuthResponse(
            jwtTokenProvider.createAccessToken(userCredentials.login(), user, 1234L),
            refreshToken.getToken()
        );
    }

    @Nonnull
    @Override
    @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        log.info("refresh: started with request {}", refreshToken);
        RefreshTokenClaims claims = jwtTokenProvider.validateToken(refreshToken, RefreshTokenClaims.class)
            .orElseThrow(UnauthorizedException::new);

        Session session = sessionService.findSessionByUuid(claims.getToken())
            .orElseThrow(() -> new UnauthorizedException("Не найдено"));

        return refreshAuthenticationResponseDto(claims, session);
    }

    @Nonnull
    private AuthResponse refreshAuthenticationResponseDto(RefreshTokenClaims claims, Session session) {
        log.info("refresh authentication response dto");
        String username = claims.getUsername();

        RefreshToken refreshToken = jwtTokenProvider.createRefreshToken(username);
        BusinessUser user = session.getUser();
        String newAccessToken = jwtTokenProvider.createAccessToken(username, user, session.getId());
        sessionService.updateSession(session, refreshToken);
        return AuthResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(refreshToken.getToken())
            .build();
    }
}
