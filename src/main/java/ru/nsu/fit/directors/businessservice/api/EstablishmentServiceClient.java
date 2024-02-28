package ru.nsu.fit.directors.businessservice.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseShortEstablishmentInfo;

@FeignClient("establishment-service")
public interface EstablishmentServiceClient {
    @RequestMapping(method = RequestMethod.POST, value = "/internal/establishment")
    Long createEstablishment(@RequestParam Long ownerId);

    @RequestMapping(method = RequestMethod.GET, value = "/internal/establishment/owner")
    List<ResponseShortEstablishmentInfo> getEstablishmentsByOwner(@RequestParam Long ownerId);
}
