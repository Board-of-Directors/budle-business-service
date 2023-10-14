package ru.nsu.fit.directors.businessservice.service;


import ru.nsu.fit.directors.businessservice.dto.BusinessUserLoginRequest;
import ru.nsu.fit.directors.businessservice.dto.request.BusinessUserRegisterRequest;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface BusinessUserService {
    /**
     * Регистрация бизнес-пользователя в системе.
     *
     * @param businessUserRegisterRequest запрос на создание бизнес пользователя
     */
    void registerBusinessUser(BusinessUserRegisterRequest businessUserRegisterRequest);

    /**
     * Авторизация бизнес-пользователя в системе.
     *
     * @param businessUserLoginRequest запрос на авторизацию бизнес-пользователя
     */

    void loginBusinessUser(BusinessUserLoginRequest businessUserLoginRequest);
}
