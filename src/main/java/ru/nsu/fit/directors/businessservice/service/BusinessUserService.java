package ru.nsu.fit.directors.businessservice.service;

import ru.nsu.fit.directors.businessservice.dto.request.BusinessUserRegisterRequest;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;

import javax.annotation.Nonnull;
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
     * Получить бизнес-пользователя по логину.
     *
     * @param login логин бизнес-пользователя
     * @return сущность пользователя
     */
    @Nonnull
    BusinessUser getByLogin(String login);

    /**
     * Получить бизнес-пользователя по идентификатору.
     *
     * @param businessUserId идентификатор бизнес пользователя
     * @return данные пользователя
     */
    @Nonnull
    BusinessUser getById(Long businessUserId);

    /**
     * Сохранить пользователя.
     *
     * @param businessUser пользователь
     */
    void save(BusinessUser businessUser);

    /**
     * Получить пользователя по токену.
     *
     * @param token токен пользователя
     * @return данные пользователя
     */
    @Nonnull
    BusinessUser getByToken(String token);
}
