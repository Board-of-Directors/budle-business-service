package ru.nsu.fit.directors.businessservice.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseOrderDto;

@FeignClient("order-service")
public interface OrderServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/order/establishment")
    List<ResponseOrderDto> getEstablishmentOrders(@RequestParam Long establishmentId);
}
