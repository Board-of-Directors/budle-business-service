package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.api.OrderServiceClient;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseMessageDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.businessservice.event.OrderStatusChangedEvent;
import ru.nsu.fit.directors.businessservice.exceptions.NotEnoughRightException;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;

@Service
@ParametersAreNonnullByDefault
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final RoleService roleService;
    private final KafkaTemplate<String, OrderStatusChangedEvent> kafkaTemplate;
    private final OrderServiceClient orderServiceClient;

    @Override
    public List<ResponseOrderDto> getOrdersByEstablishment(Long establishmentId) {
        if (roleService.isUserOwner(establishmentId) || roleService.isUserWorker(establishmentId)) {
            return orderServiceClient.getEstablishmentOrders(establishmentId).getBody().getResult();
        }
        throw new NotEnoughRightException();
    }

    @Override
    public void setOrderStatus(Long orderId, Long establishmentId, int status) {
        if (roleService.isUserOwner(establishmentId) || roleService.isUserWorker(establishmentId)) {
            kafkaTemplate.send(
                "orderTopic",
                OrderStatusChangedEvent.builder()
                    .status(status)
                    .orderId(orderId)
                    .establishmentId(establishmentId)
                    .build()
            );
        }
    }

    @Override
    public List<ResponseMessageDto> getMessages(Long userId, Long orderId) {
        return orderServiceClient.getMessages(userId, orderId).getBody().getResult();
    }
}
