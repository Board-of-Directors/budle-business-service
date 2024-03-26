package ru.nsu.fit.directors.businessservice.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.businessservice.dto.response.OutputMessageDto;

@RestController
@RequiredArgsConstructor
public class ChatController {

    @MessageMapping("/business/chat")
    @SendTo("/business/topic/messages")
    public OutputMessageDto send(Message<String> message) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessageDto(message.getPayload(), time);
    }
}
