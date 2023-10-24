package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.api.OrderApi;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.businessservice.event.OrderStatusChangedEvent;
import ru.nsu.fit.directors.businessservice.exceptions.NotEnoughRightException;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Service
@ParametersAreNonnullByDefault
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final SecurityService securityService;
    private final KafkaTemplate<String, OrderStatusChangedEvent> kafkaTemplate;
    private final OrderApi orderApi;

    @Override
    public List<ResponseOrderDto> getOrdersByEstablishment(Long establishmentId) {
        if (isUserOwner(establishmentId)) {
            return orderApi.syncListGetWithParams(
                uriBuilder -> uriBuilder.path("/order/establishment")
                    .queryParam("establishmentId", establishmentId)
                    .build(),
                new ParameterizedTypeReference<>() {
                }
            );
        }
        throw new NotEnoughRightException();
    }

    @Override
    public void setOrderStatus(Long orderId, Long establishmentId, int status) {
        if (isUserOwner(establishmentId)) {
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

    private boolean isUserOwner(Long establishmentId) {
        return securityService.getLoggedInUser().getCompanies()
            .stream()
            .anyMatch(company -> company.getId().equals(establishmentId));
    }
}
