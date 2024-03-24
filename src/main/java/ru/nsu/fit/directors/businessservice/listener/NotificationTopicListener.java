package ru.nsu.fit.directors.businessservice.listener;

import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.dto.BusinessOrderNotificationEvent;
import ru.nsu.fit.directors.businessservice.service.NotificationService;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
@KafkaListener(topics = "notificationTopic")
public class NotificationTopicListener {
    private final NotificationService notificationService;

    @KafkaHandler
    public void handleOrderNotification(BusinessOrderNotificationEvent orderNotificationEvent){
        notificationService.handleOrderNotification(orderNotificationEvent);

    }
}
