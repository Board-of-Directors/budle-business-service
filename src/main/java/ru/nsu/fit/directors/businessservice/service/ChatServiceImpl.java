package ru.nsu.fit.directors.businessservice.service;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.dto.request.MessageDto;
import ru.nsu.fit.directors.businessservice.event.BusinessMessageEvent;
import ru.nsu.fit.directors.businessservice.event.UserMessageEvent;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class ChatServiceImpl implements ChatService {
    private static final String CHAT_TOPIC = "chatTopic";
    private final KafkaTemplate<String, BusinessMessageEvent> kafkaTemplate;
    private final SecurityService securityService;
    private final OrderService orderService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void save(MessageDto messageDto, Long orderId) {
        BusinessUser user = securityService.getLoggedInUser();
        kafkaTemplate.send(
            CHAT_TOPIC,
            new BusinessMessageEvent(user.getId(), orderId, messageDto.message())
        );
    }

    @Nonnull
    @Override
    public List<MessageDto> getChat(Long orderId) {
        BusinessUser user = securityService.getLoggedInUser();
        return Objects.requireNonNull(orderService.getMessages(user.getId(), orderId));
    }

    @Override
    public void handleMessage(UserMessageEvent userMessageEvent) {
        simpMessagingTemplate.convertAndSend("/topic/" + userMessageEvent.orderId(), userMessageEvent);
    }
}
