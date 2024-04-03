package ru.nsu.fit.directors.businessservice.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.fit.directors.businessservice.dto.request.MessageDto;
import ru.nsu.fit.directors.businessservice.dto.response.OutputMessageDto;
import ru.nsu.fit.directors.businessservice.service.ChatService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/send/{orderId}")
    @SendTo("/topic/{orderId}")
    public OutputMessageDto send(@DestinationVariable Long orderId, Message<MessageDto> message) {
        log.info("received order message {}", orderId);
        chatService.save(message.getPayload(), orderId);
        return new OutputMessageDto("Hello " + orderId + "Ololo", "ololo");
    }

    @GetMapping
    public List<MessageDto> getMessages(@RequestParam Long orderId) {
        return chatService.getChat(orderId);
    }
}