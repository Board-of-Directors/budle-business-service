package ru.nsu.fit.directors.businessservice.service;

import java.util.List;

import reactor.core.publisher.Flux;
import ru.nsu.fit.directors.businessservice.dto.BusinessOrderNotificationEvent;
import ru.nsu.fit.directors.businessservice.dto.response.NotificationDto;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;

public interface NotificationService {
    void sendRegistrationNotification(BusinessUser businessUser, String password);

    void handleOrderNotification(BusinessOrderNotificationEvent orderNotificationEvent);

    List<NotificationDto> getNotifications();

    Flux<NotificationDto> getFluxNotifications();
}
