package ru.nsu.fit.directors.businessservice.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import ru.nsu.fit.directors.businessservice.dto.response.OutputMessageDto;

@Controller
@RequiredArgsConstructor
public class ChatController {

    @MessageMapping("/send")
    @SendTo("/topic/greetings")
    public OutputMessageDto greeting(Message<String> message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new OutputMessageDto("Hello, " + HtmlUtils.htmlEscape(message.getPayload()) + "!", "ololo");
    }

    @MessageMapping("/send/{orderId}")
    @SendTo("/topic/{orderId}")
    public OutputMessageDto greeting(@DestinationVariable Long orderId) {
        return new OutputMessageDto("Hello " + orderId + "Ololo", "ololo");
    }
}
