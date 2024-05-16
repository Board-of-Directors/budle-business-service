package ru.nsu.fit.directors.businessservice.service;

import java.time.Instant;
import java.util.Optional;

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
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Transactional
    @Override
    public Session createSession(BusinessUser user, RefreshToken refreshToken) {
        log.info("createSession: start create session");
        var sessionOpt = sessionRepository.findByUser(user);

        if (sessionOpt.isPresent()) {
            return updateSession(sessionOpt.get(), refreshToken);
        }

        return sessionRepository.save(new Session()
            .setUser(user)
            .setRefreshTokenUuid(refreshToken.getUuid())
            .setExpireAt(refreshToken.getExpireAt())
            .setCreateAt(Instant.now())
            .setUpdateAt(Instant.now()));
    }

    @Override
    public Session updateSession(Session toUpdate, RefreshToken refreshToken) {
        log.info("updateSession: start update session and save");
        toUpdate.setRefreshTokenUuid(refreshToken.getUuid());
        toUpdate.setExpireAt(refreshToken.getExpireAt());
        return sessionRepository.save(toUpdate);
    }

    @Override
    public Optional<Session> findSessionByUuid(String tokenUuid) {
        return sessionRepository.findByRefreshTokenUuid(tokenUuid);
    }
}
