package ru.nsu.fit.directors.businessservice.service;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import reactor.core.publisher.Flux;
import ru.nsu.fit.directors.businessservice.dto.BusinessOrderNotificationEvent;
import ru.nsu.fit.directors.businessservice.dto.response.NotificationDto;

@ParametersAreNonnullByDefault
public interface NotificationService {

    void handleOrderNotification(BusinessOrderNotificationEvent orderNotificationEvent);

    List<NotificationDto> getNotifications();

    Flux<NotificationDto> getFluxNotifications();
}
