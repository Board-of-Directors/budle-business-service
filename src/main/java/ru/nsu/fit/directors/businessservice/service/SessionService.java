package ru.nsu.fit.directors.businessservice.service;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Session;
import ru.nsu.fit.directors.businessservice.security.model.RefreshToken;

@ParametersAreNonnullByDefault
public interface SessionService {
    /**
     * Создать новую сессию для пользователя.
     *
     * @param user         пользователь
     * @param refreshToken токен обновления пользователя
     */
    void createSession(BusinessUser user, RefreshToken refreshToken);

    /**
     * Обновить сессию пользователь
     *
     * @param toUpdate     сессия для обновления
     * @param refreshToken токен для обновления
     * @return обновленная сессия
     */
    @Nonnull
    Session updateSession(Session toUpdate, RefreshToken refreshToken);

    /**
     * Найти сессию по идентификатору.
     *
     * @param tokenUuid идентификатор сессии.
     * @return сессия или Optional.empty()
     */
    @Nonnull
    Optional<Session> findSessionByUuid(String tokenUuid);
}
