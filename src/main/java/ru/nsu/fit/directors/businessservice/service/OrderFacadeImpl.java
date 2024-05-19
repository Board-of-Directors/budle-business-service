package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.api.OrderServiceClient;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseMessageDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.businessservice.event.OrderStatusChangedEvent;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class OrderFacadeImpl implements OrderFacade {
    private final EmployeeService employeeService;
    private final KafkaTemplate<String, OrderStatusChangedEvent> kafkaTemplate;
    private final OrderServiceClient orderServiceClient;

    @Nonnull
    @Override
    public List<ResponseOrderDto> getOrdersByEstablishment(Long establishmentId) {
        employeeService.validateWorker(establishmentId);
        return orderServiceClient.getEstablishmentOrders(establishmentId).getBody().getResult();
    }

    @Override
    public void setOrderStatus(Long orderId, Long establishmentId, int status) {
        employeeService.validateWorker(establishmentId);
        kafkaTemplate.send(
            "orderTopic",
            OrderStatusChangedEvent.builder()
                .status(status)
                .orderId(orderId)
                .establishmentId(establishmentId)
                .build()
        );
    }

    @Nonnull
    @Override
    public List<ResponseMessageDto> getMessages(Long userId, Long orderId) {
        return orderServiceClient.getMessages(userId, orderId).getBody().getResult();
    }
}
