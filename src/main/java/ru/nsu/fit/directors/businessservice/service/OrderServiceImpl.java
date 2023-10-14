package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.businessservice.event.OrderStatusChangedEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

@Service
@ParametersAreNonnullByDefault
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final WebClient.Builder orderClientBuilder;
    private final SecurityService securityService;
    private final KafkaTemplate kafkaTemplate;

    @Override
    public List<ResponseOrderDto> getOrdersByEstablishment(Long establishmentId) {
        if (isUserOwner(establishmentId)) {
            ParameterizedTypeReference<List<ResponseOrderDto>> ref = new ParameterizedTypeReference<>() {
            };
            var response = orderClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder.path("/order/establishment")
                    .queryParam("establishmentId", establishmentId)
                    .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(ref)
                .block();
            return Optional.ofNullable(response).map(HttpEntity::getBody).orElse(null);
        }
        throw new RuntimeException("No rights for this operation");
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
        return securityService.getLoggedInUser().getCreatorOfCompanies()
            .stream()
            .anyMatch(company -> company.getId().equals(establishmentId));
    }
}
