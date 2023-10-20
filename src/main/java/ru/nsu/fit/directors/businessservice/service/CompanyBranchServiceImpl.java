package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.nsu.fit.directors.businessservice.dto.request.CompanyCreateRequest;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseShortEstablishmentInfo;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.repository.CompanyBranchRepository;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

@Service
@ParametersAreNonnullByDefault
@RequiredArgsConstructor
public class CompanyBranchServiceImpl implements CompanyBranchService {
    private final WebClient.Builder establishmentClientBuilder;
    private final SecurityService securityService;
    private final CompanyBranchRepository companyBranchRepository;

    @Override
    public void createCompanyBranch(CompanyCreateRequest companyCreateRequest) {
        ResponseEntity<Long> response = establishmentClientBuilder.build().post()
            .uri(uriBuilder ->
                uriBuilder.path("/establishment")
                    .queryParam("ownerId", securityService.getLoggedInUser().getId())
                    .build())
            .body(Mono.just(companyCreateRequest), CompanyCreateRequest.class)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(Long.class)
            .block();
        Optional.ofNullable(response)
            .map(HttpEntity::getBody)
            .ifPresent(id -> companyBranchRepository.save(new Company()
                .setId(id)
                .setBusinessUser(securityService.getLoggedInUser()))
            );

    }

    @Override
    public List<ResponseShortEstablishmentInfo> getEstablishmentsByOwner() {
        BusinessUser user = securityService.getLoggedInUser();
        ParameterizedTypeReference<List<ResponseShortEstablishmentInfo>> ref
            = new ParameterizedTypeReference<>() {
        };
        var response = establishmentClientBuilder.build()
            .get()
            .uri(uriBuilder -> uriBuilder.path("/establishment/owner")
                .queryParam("ownerId", user.getId())
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(ref)
            .block();
        return Optional.ofNullable(response).map(HttpEntity::getBody).orElse(null);
    }
}
