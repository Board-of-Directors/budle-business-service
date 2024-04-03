package ru.nsu.fit.directors.businessservice.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.fit.directors.businessservice.dto.request.MessageDto;
import ru.nsu.fit.directors.businessservice.service.ChatService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/send/{orderId}")
    public void send(@DestinationVariable Long orderId, Message<MessageDto> message, HttpServletRequest request) {
        log.info(request.toString());
        log.info(SecurityContextHolder.getContext().toString());
        log.info("received order message {}", orderId);
        chatService.save(message.getPayload(), orderId);
    }

    @GetMapping
    public List<MessageDto> getMessages(@RequestParam Long orderId) {
        return chatService.getChat(orderId);
    }
}
