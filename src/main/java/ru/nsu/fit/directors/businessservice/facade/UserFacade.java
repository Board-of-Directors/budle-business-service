package ru.nsu.fit.directors.businessservice.facade;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.businessservice.dto.AuthResponse;
import ru.nsu.fit.directors.businessservice.dto.BusinessUserLoginRequest;

@ParametersAreNonnullByDefault
public interface UserFacade {
    /**
     * Выполнить вход с помощью реквизитов пользователя.
     *
     * @param loginRequest реквизиты пользователя
     * @return объект, содержащий данные об авторизации
     */
    @Nonnull
    AuthResponse loginCredentials(BusinessUserLoginRequest loginRequest);

    /**
     * Обновить токен авторизации с помощью токена обновления.
     *
     * @param refreshToken токен обновления
     * @return данные авторизации
     */
    @Nonnull
    AuthResponse refreshToken(String refreshToken);
}
