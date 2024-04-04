package ru.nsu.fit.directors.businessservice.listener;

import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.event.UserMessageEvent;
import ru.nsu.fit.directors.businessservice.service.ChatService;

@Slf4j
@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
@KafkaListener(topics = "user-chat-topic")
public class ChatTopicListener {
    private final ChatService chatService;

    @KafkaHandler
    public void handleUserMessage(UserMessageEvent userMessageEvent) {
        log.info("Receive user message {}", userMessageEvent);
        chatService.handleMessage(userMessageEvent);
    }
}
