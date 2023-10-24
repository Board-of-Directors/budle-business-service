package ru.nsu.fit.directors.businessservice.service;

import ru.nsu.fit.directors.businessservice.model.BusinessUser;

public interface NotificationService {
    void sendRegistrationNotification(BusinessUser businessUser, String password);
}
