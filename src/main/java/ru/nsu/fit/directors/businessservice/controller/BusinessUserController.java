package ru.nsu.fit.directors.businessservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.businessservice.dto.AuthResponse;
import ru.nsu.fit.directors.businessservice.dto.BusinessUserLoginRequest;
import ru.nsu.fit.directors.businessservice.dto.ChangeBusinessUserRequest;
import ru.nsu.fit.directors.businessservice.dto.ResponseAuthDto;
import ru.nsu.fit.directors.businessservice.dto.request.BusinessUserRegisterRequest;
import ru.nsu.fit.directors.businessservice.dto.request.CompanyCreateRequestV2;
import ru.nsu.fit.directors.businessservice.dto.response.BusinessUserCredentialsResponse;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseMessageDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseShortEstablishmentInfo;
import ru.nsu.fit.directors.businessservice.facade.BusinessUserFacade;
import ru.nsu.fit.directors.businessservice.facade.UserFacade;
import ru.nsu.fit.directors.businessservice.security.JwtTokenRepository;
import ru.nsu.fit.directors.businessservice.service.ChatService;
import ru.nsu.fit.directors.businessservice.service.CompanyBranchService;
import ru.nsu.fit.directors.businessservice.service.OrderFacade;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;

@RestController
@ParametersAreNonnullByDefault
@RequiredArgsConstructor
@RequestMapping(value = "/business")
@Validated
public class BusinessUserController {
    private final BusinessUserFacade businessUserFacade;
    private final CompanyBranchService companyBranchService;
    private final OrderFacade orderFacade;
    private final ChatService chatService;
    private final UserFacade userFacade;
    private final JwtTokenRepository jwtTokenRepository;

    @PostMapping(value = "/registration")
    public void register(@RequestBody @Valid BusinessUserRegisterRequest businessUserRegisterRequest) {
        businessUserFacade.register(businessUserRegisterRequest);
    }

    @PutMapping(value = "/change")
    public void change(@RequestBody @Valid ChangeBusinessUserRequest changeBusinessUserRequest) {
        businessUserFacade.changeBusinessUser(changeBusinessUserRequest);
    }

    @GetMapping("/chat/history")
    public List<ResponseMessageDto> getMessages(@RequestParam Long orderId) {
        return chatService.getChat(orderId);
    }

    @GetMapping(value = "/me")
    public BusinessUserCredentialsResponse me() {
        return businessUserFacade.getActiveUser();
    }

    @PostMapping(value = "/login/jwt", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseAuthDto> loginJwt(@RequestBody BusinessUserLoginRequest loginRequest) {
        return ResponseEntity.ok(createResponseAuthDto(userFacade.loginCredentials(loginRequest)));
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<ResponseAuthDto> refreshJwt(@CookieValue(name = "refreshToken") String refreshToken) {
        return ResponseEntity.ok(createResponseAuthDto(userFacade.refreshToken(refreshToken)));
    }

    @PostMapping("v2/company")
    public void create(@RequestBody @Valid CompanyCreateRequestV2 companyCreateRequest) {
        companyBranchService.createCompanyBranch(companyCreateRequest);
    }

    @DeleteMapping("/v2/company")
    public void delete(@RequestParam Long establishmentId) {
        companyBranchService.deleteCompany(establishmentId);
    }

    @PutMapping("/v2/company")
    public void change(@RequestParam Long establishmentId, CompanyCreateRequestV2 changeRequest){
        companyBranchService.changeCompany(establishmentId, changeRequest);
    }

    @GetMapping(value = "/establishments")
    public List<ResponseShortEstablishmentInfo> ownerEstablishments(
        @RequestParam(required = false) String name
    ) {
        return companyBranchService.getEstablishmentsByOwner(name);
    }

    @GetMapping(value = "/orders")
    public List<ResponseOrderDto> getOrders(@RequestParam Long establishmentId) {
        return orderFacade.getOrdersByEstablishment(establishmentId);
    }

    @PostMapping(value = "/order")
    public void setOrderStatus(
        @RequestParam Long orderId,
        @RequestParam Long establishmentId,
        @RequestParam int status
    ) {
        orderFacade.setOrderStatus(orderId, establishmentId, status);
    }

    @Nonnull
    private ResponseAuthDto createResponseAuthDto(AuthResponse authResponse) {
        jwtTokenRepository.setTokenToCookie(authResponse.refreshToken());
        return new ResponseAuthDto(authResponse.accessToken());
    }
}
