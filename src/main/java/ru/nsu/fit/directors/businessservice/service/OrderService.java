package ru.nsu.fit.directors.businessservice.service;

import ru.nsu.fit.directors.businessservice.dto.response.ResponseMessageDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseOrderDto;

import java.util.List;

public interface OrderService {
    List<ResponseOrderDto> getOrdersByEstablishment(Long establishmentId);

    void setOrderStatus(Long orderId, Long establishmentId, int status);

    List<ResponseMessageDto> getMessages(Long userId, Long orderId);
}
