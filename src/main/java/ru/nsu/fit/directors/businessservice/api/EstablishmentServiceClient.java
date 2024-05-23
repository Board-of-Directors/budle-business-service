package ru.nsu.fit.directors.businessservice.api;

import java.util.Collection;
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
import ru.nsu.fit.directors.businessservice.dto.CreateAnswerRequest;
import ru.nsu.fit.directors.businessservice.dto.request.ChangeCategoryRequest;
import ru.nsu.fit.directors.businessservice.dto.request.ChangeProductRequest;
import ru.nsu.fit.directors.businessservice.dto.request.CompanyCreateRequestV2;
import ru.nsu.fit.directors.businessservice.dto.request.RequestCategoryDto;
import ru.nsu.fit.directors.businessservice.dto.request.RequestProductDto;
import ru.nsu.fit.directors.businessservice.dto.response.BaseResponse;
import ru.nsu.fit.directors.businessservice.dto.response.CompanyDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseShortEstablishmentInfo;

@FeignClient(
    value = "establishment-service",
    configuration = {ClientConfiguration.class, CustomErrorDecoderConfiguration.class}
)
public interface EstablishmentServiceClient {

    @RequestMapping(method = RequestMethod.POST, value = "/internal/establishment/v2/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<Long>> createEstablishmentV2(
        @RequestParam Long ownerId,
        @RequestBody CompanyCreateRequestV2 companyCreateRequest
    );

    @RequestMapping(method = RequestMethod.GET, value = "/internal/establishment/owner", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<List<ResponseShortEstablishmentInfo>>> getEstablishmentsByOwner(
        @RequestParam Long ownerId,
        @RequestParam String name
    );

    @RequestMapping(method = RequestMethod.POST, value = "/internal/menu", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void add(@RequestBody RequestCategoryDto category);

    @RequestMapping(method = RequestMethod.POST, value = "/internal/menu/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void addProduct(@RequestBody RequestProductDto product);

    @RequestMapping(method = RequestMethod.DELETE, value = "/internal/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteCategory(@RequestParam long categoryId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/internal/menu/product", produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteProduct(@RequestParam long productId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/internal/establishment")
    void deleteEstablishment(@RequestParam Long establishmentId);

    @RequestMapping(method = RequestMethod.PUT, value = "/internal/establishment")
    void update(@RequestParam Long establishmentId, @RequestBody CompanyCreateRequestV2 establishmentDto);

    @RequestMapping(method = RequestMethod.POST, value = "/internal/review/answer")
    void createAnswer(@RequestBody CreateAnswerRequest createAnswerRequest);

    @RequestMapping(method = RequestMethod.PUT, value = "/internal/menu")
    void changeCategory(@RequestBody ChangeCategoryRequest changeCategoryRequest);

    @RequestMapping(method = RequestMethod.PUT, value = "/internal/menu/product")
    void changeProduct(@RequestBody ChangeProductRequest changeProductRequest);

    @RequestMapping(method = RequestMethod.GET, value = "/internal/establishment", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<List<CompanyDto>>> getCompaniesByIds(@RequestParam Collection<Long> ids);
}
