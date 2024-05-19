package ru.nsu.fit.directors.businessservice.service;

import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.businessservice.model.BusinessUser;

@ParametersAreNonnullByDefault
public interface MailNotificationService {
    /**
     * Отправить уведомление о регистрации.
     *
     * @param user     пользователь
     * @param password пароль
     */
    void sendRegistrationNotification(BusinessUser user, String password);
}
