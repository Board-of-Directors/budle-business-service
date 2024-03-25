package ru.nsu.fit.directors.businessservice.service;

import java.util.List;

import reactor.core.publisher.Flux;
import ru.nsu.fit.directors.businessservice.dto.BusinessOrderNotificationEvent;
import ru.nsu.fit.directors.businessservice.dto.response.NotificationDto;

public interface NotificationService {

    void handleOrderNotification(BusinessOrderNotificationEvent orderNotificationEvent);

    List<NotificationDto> getNotifications();

    Flux<NotificationDto> getFluxNotifications();
}
