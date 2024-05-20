package ru.nsu.fit.directors.businessservice.facade;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.businessservice.dto.ChangeBusinessUserRequest;
import ru.nsu.fit.directors.businessservice.dto.request.BusinessUserRegisterRequest;
import ru.nsu.fit.directors.businessservice.dto.response.BusinessUserCredentialsResponse;

@ParametersAreNonnullByDefault
public interface BusinessUserFacade {
    /**
     * Зарегистрировать пользователя.
     *
     * @param businessUserRegisterRequest запрос на регистрацию
     */
    void register(BusinessUserRegisterRequest businessUserRegisterRequest);

    /**
     * Получить данные активного пользователя.
     *
     * @return данные пользователя
     */
    @Nonnull
    BusinessUserCredentialsResponse getActiveUser();

    /**
     * Изменить данные бизнес пользователя.
     *
     * @param changeBusinessUserRequest новые данные
     */
    void changeBusinessUser(ChangeBusinessUserRequest changeBusinessUserRequest);

    /**
     * Получить токен приглашения.
     *
     * @return токен
     */
    @Nonnull
    String getInviteToken();
}
