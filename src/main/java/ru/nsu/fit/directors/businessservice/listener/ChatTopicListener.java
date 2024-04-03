package ru.nsu.fit.directors.businessservice.listener;

import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.event.UserMessageEvent;
import ru.nsu.fit.directors.businessservice.service.ChatService;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
@KafkaListener(topics = "chatTopic")
public class ChatTopicListener {
    private final ChatService chatService;

    @KafkaHandler
    public void handleUserMessage(UserMessageEvent userMessageEvent) {
        chatService.handleMessage(userMessageEvent);
    }
}
