package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.api.OrderServiceClient;
import ru.nsu.fit.directors.businessservice.dto.response.BaseResponse;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseMessageDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.businessservice.event.OrderStatusChangedEvent;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.model.Option;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class OrderFacadeImpl implements OrderFacade {
    private final EmployeeService employeeService;
    private final KafkaTemplate<String, OrderStatusChangedEvent> kafkaTemplate;
    private final OrderServiceClient orderServiceClient;
    private final CompanyService companyService;

    @Nonnull
    @Override
    public List<ResponseOrderDto> getOrdersByEstablishment(Long establishmentId) {
        Company company = companyService.getById(establishmentId);
        employeeService.validateWorker(company, Option.SEARCHING_ORDERS);
        return Optional.ofNullable(orderServiceClient.getEstablishmentOrders(establishmentId).getBody())
            .map(BaseResponse::getResult)
            .orElseGet(List::of);
    }

    @Override
    public void setOrderStatus(Long orderId, Long establishmentId, int status) {
        Company company = companyService.getById(establishmentId);
        employeeService.validateWorker(company, Option.CHANGING_ORDER_STATUSES);
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
        return Optional.ofNullable(orderServiceClient.getMessages(userId, orderId).getBody())
            .map(BaseResponse::getResult)
            .orElseGet(List::of);
    }
}
