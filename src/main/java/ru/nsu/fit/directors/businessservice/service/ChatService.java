package ru.nsu.fit.directors.businessservice.service;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.businessservice.dto.request.MessageDto;
import ru.nsu.fit.directors.businessservice.event.UserMessageEvent;

@ParametersAreNonnullByDefault
public interface ChatService {
    /**
     * Сохранить сообщение из чата.
     *
     * @param chatMessage сообщение
     */
    void save(MessageDto chatMessage, Long orderID);

    /**
     * Получить историю сообщений в рамках брони.
     *
     * @param orderId идентификатор брони
     */
    @Nonnull
    List<MessageDto> getChat(Long orderId);

    /**
     * Обработать сообщение от пользователя.
     *
     * @param userMessageEvent сообщение от пользователя
     */
    void handleMessage(UserMessageEvent userMessageEvent);
}
