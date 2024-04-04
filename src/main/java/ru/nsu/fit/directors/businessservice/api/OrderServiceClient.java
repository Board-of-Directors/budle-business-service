package ru.nsu.fit.directors.businessservice.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.fit.directors.businessservice.configuration.ClientConfiguration;
import ru.nsu.fit.directors.businessservice.dto.response.BaseResponse;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseMessageDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseOrderDto;

@FeignClient(value = "order-service", configuration = ClientConfiguration.class)
public interface OrderServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/order/establishment")
    ResponseEntity<BaseResponse<List<ResponseOrderDto>>> getEstablishmentOrders(@RequestParam Long establishmentId);

    @RequestMapping(method = RequestMethod.GET, value = "/order/message/business", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<List<ResponseMessageDto>>> getMessages(
        @RequestParam Long businessId,
        @RequestParam Long orderId
    );
}
