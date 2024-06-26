package ru.nsu.fit.directors.businessservice.service;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.dto.request.MessageDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseMessageDto;
import ru.nsu.fit.directors.businessservice.event.BusinessMessageEvent;
import ru.nsu.fit.directors.businessservice.event.UserMessageEvent;
import ru.nsu.fit.directors.businessservice.exceptions.UserNotLoggedInException;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.repository.BusinessUserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class ChatServiceImpl implements ChatService {
    private static final String CHAT_TOPIC = "business-chat-topic";
    private static final String ORDER_TOPIC = "orderTopic";
    private final KafkaTemplate<String, BusinessMessageEvent> kafkaTemplate;
    private final EmployeeService employeeService;
    private final OrderFacade orderFacade;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final BusinessUserRepository businessUserRepository;

    @Override
    public void save(MessageDto messageDto, Long orderId) {
        BusinessUser businessUser = businessUserRepository.findById(messageDto.userId())
            .orElseThrow(UserNotLoggedInException::new);
        kafkaTemplate.send(
            CHAT_TOPIC,
            new BusinessMessageEvent(businessUser.getId(), orderId, messageDto.message())
        );
        kafkaTemplate.send(
            ORDER_TOPIC,
            new BusinessMessageEvent(businessUser.getId(), orderId, messageDto.message())
        );
        log.info("Successfully sent to CHAT_TOPIC {}", messageDto);
    }

    @Nonnull
    @Override
    public List<ResponseMessageDto> getChat(Long orderId) {
        BusinessUser user = employeeService.getLoggedInUser();
        return orderFacade.getMessages(user.getId(), orderId);
    }

    @Override
    public void handleMessage(UserMessageEvent userMessageEvent) {
        simpMessagingTemplate.convertAndSend("/topic/" + userMessageEvent.orderId(), userMessageEvent);
    }
}
