package ru.nsu.fit.directors.businessservice.service;

import jakarta.annotation.Nonnull;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseMessageDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseOrderDto;

import java.util.List;

public interface OrderFacade {
    /**
     * Получить заказы заведения.
     *
     * @param establishmentId идентификатор заведения
     * @return список заказов
     */
    @Nonnull
    List<ResponseOrderDto> getOrdersByEstablishment(Long establishmentId);

    /**
     * Поменять статус заказа.
     *
     * @param orderId         идентификатор заказа
     * @param establishmentId идентификатор заведения
     * @param status          новый статус
     */
    void setOrderStatus(Long orderId, Long establishmentId, int status);

    /**
     * Получить сообщения из чата заказа.
     *
     * @param userId  идентификатор юзера
     * @param orderId идентификатор заказа
     * @return список сообщений
     */
    @Nonnull
    List<ResponseMessageDto> getMessages(Long userId, Long orderId);
}
