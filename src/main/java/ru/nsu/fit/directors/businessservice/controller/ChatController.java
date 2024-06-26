package ru.nsu.fit.directors.businessservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.nsu.fit.directors.businessservice.dto.request.MessageDto;
import ru.nsu.fit.directors.businessservice.service.ChatService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/send/{orderId}")
    public void send(
        @DestinationVariable Long orderId,
        Message<MessageDto> message
    ) {
        log.info("received order message {}", orderId);
        log.info("received message {}", message);
        chatService.save(message.getPayload(), orderId);
    }
}
