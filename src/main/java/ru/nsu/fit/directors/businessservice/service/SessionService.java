package ru.nsu.fit.directors.businessservice.service;

import java.util.Optional;

import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Session;
import ru.nsu.fit.directors.businessservice.security.model.RefreshToken;

public interface SessionService {
    Session createSession(BusinessUser user, RefreshToken refreshToken);

    Session updateSession(Session toUpdate, RefreshToken refreshToken);

    Optional<Session> findSessionByUuid(String tokenUuid);
}
