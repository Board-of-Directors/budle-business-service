package ru.nsu.fit.directors.businessservice.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.fit.directors.businessservice.configuration.ClientConfiguration;
import ru.nsu.fit.directors.businessservice.configuration.CustomErrorDecoderConfiguration;
import ru.nsu.fit.directors.businessservice.dto.request.CompanyCreateRequest;
import ru.nsu.fit.directors.businessservice.dto.request.CompanyCreateRequestV2;
import ru.nsu.fit.directors.businessservice.dto.response.BaseResponse;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseShortEstablishmentInfo;

@FeignClient(
    value = "establishment-service",
    configuration = {ClientConfiguration.class, CustomErrorDecoderConfiguration.class}
)
public interface EstablishmentServiceClient {
    @RequestMapping(method = RequestMethod.POST, value = "/internal/establishment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<Long>> createEstablishment(
        @RequestParam Long ownerId,
        @RequestBody CompanyCreateRequest companyCreateRequest
    );

    @RequestMapping(method = RequestMethod.POST, value = "/internal/establishment/v2/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<BaseResponse<Long>> createEstablishmentV2(
        @RequestParam Long ownerId,
        @RequestBody CompanyCreateRequestV2 companyCreateRequest
    );

    @RequestMapping(method = RequestMethod.GET, value = "/internal/establishment/owner", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<List<ResponseShortEstablishmentInfo>>> getEstablishmentsByOwner(@RequestParam Long ownerId);
}
