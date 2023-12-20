package ru.nsu.fit.directors.businessservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.businessservice.dto.BusinessUserLoginRequest;
import ru.nsu.fit.directors.businessservice.dto.request.BusinessUserRegisterRequest;
import ru.nsu.fit.directors.businessservice.dto.request.CompanyCreateRequest;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseShortEstablishmentInfo;
import ru.nsu.fit.directors.businessservice.service.BusinessUserService;
import ru.nsu.fit.directors.businessservice.service.CompanyBranchService;
import ru.nsu.fit.directors.businessservice.service.OrderService;
import ru.nsu.fit.directors.businessservice.service.SecurityService;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@RestController
@ParametersAreNonnullByDefault
@RequiredArgsConstructor
@RequestMapping(value = "/business")
@Validated
public class BusinessUserController {
    private final BusinessUserService businessUserService;
    private final CompanyBranchService companyBranchService;
    private final HttpServletRequest httpServletRequest;
    private final SecurityService securityService;
    private final OrderService orderService;

    @PostMapping(value = "/registration")
    public void register(@RequestBody @Valid BusinessUserRegisterRequest businessUserRegisterRequest) {
        businessUserService.registerBusinessUser(businessUserRegisterRequest);
    }

    @PostMapping(value = "/login")
    public boolean login(@RequestBody @Valid BusinessUserLoginRequest businessUserLoginRequest) {
        businessUserService.loginBusinessUser(businessUserLoginRequest);
        securityService.autoLogin(
            businessUserLoginRequest.login(),
            businessUserLoginRequest.password(),
            httpServletRequest
        );
        return true;
    }

    @PostMapping("/company")
    public void create(@RequestBody CompanyCreateRequest companyCreateRequest) {
        companyBranchService.createCompanyBranch(companyCreateRequest);
    }

    @GetMapping(value = "/establishments")
    public List<ResponseShortEstablishmentInfo> ownerEstablishments(
        @RequestParam(required = false) String name
    ) {
        return companyBranchService.getEstablishmentsByOwner(name);
    }

    @GetMapping(value = "/orders")
    public List<ResponseOrderDto> getOrders(@RequestParam Long establishmentId) {
        return orderService.getOrdersByEstablishment(establishmentId);
    }

    @PostMapping(value = "/order")
    public void setOrderStatus(
        @RequestParam Long orderId,
        @RequestParam Long establishmentId,
        @RequestParam int status
    ) {
        orderService.setOrderStatus(orderId, establishmentId, status);
    }
}
