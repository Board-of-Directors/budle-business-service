package ru.nsu.fit.directors.businessservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import ru.nsu.fit.directors.businessservice.dto.response.OutputMessageDto;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    @MessageMapping("/send")
    @SendTo("/topic/greetings")
    public OutputMessageDto greeting(Message<String> message) throws Exception {
        log.info("Received messsage {}", message);
        return new OutputMessageDto("Hello, " + HtmlUtils.htmlEscape(message.getPayload()) + "!", "ololo");
    }

    @MessageMapping("/send/{orderId}")
    @SendTo("/topic/{orderId}")
    public OutputMessageDto greeting(@DestinationVariable Long orderId) {
        log.info("received order message {}", orderId);
        return new OutputMessageDto("Hello " + orderId + "Ololo", "ololo");
    }
}
