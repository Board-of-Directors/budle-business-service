package ru.nsu.fit.directors.businessservice.service;

import java.time.Instant;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Session;
import ru.nsu.fit.directors.businessservice.repository.SessionRepository;
import ru.nsu.fit.directors.businessservice.security.model.RefreshToken;

@Slf4j
@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    @Transactional
    public void createSession(BusinessUser user, RefreshToken refreshToken) {
        sessionRepository.findByUser(user)
            .map(session -> updateSession(session, refreshToken))
            .orElseGet(() -> sessionRepository.save(newSession(user, refreshToken)));
    }

    @Nonnull
    private Session newSession(BusinessUser user, RefreshToken refreshToken) {
        return new Session()
            .setUser(user)
            .setRefreshTokenUuid(refreshToken.getUuid())
            .setExpireAt(refreshToken.getExpireAt())
            .setCreateAt(Instant.now())
            .setUpdateAt(Instant.now());
    }

    @Nonnull
    @Override
    public Session updateSession(Session toUpdate, RefreshToken refreshToken) {
        toUpdate.setRefreshTokenUuid(refreshToken.getUuid());
        toUpdate.setExpireAt(refreshToken.getExpireAt());
        return sessionRepository.save(toUpdate);
    }

    @Nonnull
    @Override
    public Optional<Session> findSessionByUuid(String tokenUuid) {
        return sessionRepository.findByRefreshTokenUuid(tokenUuid);
    }
}
