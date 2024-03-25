package ru.nsu.fit.directors.businessservice.service;

import ru.nsu.fit.directors.businessservice.model.BusinessUser;

public interface MailNotificationService {
    void sendRegistrationNotification(BusinessUser user, String password);
}
