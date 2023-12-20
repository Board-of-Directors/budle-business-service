package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.nsu.fit.directors.businessservice.api.EstablishmentApi;
import ru.nsu.fit.directors.businessservice.dto.request.CompanyCreateRequest;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseShortEstablishmentInfo;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.repository.CompanyBranchRepository;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;

@Service
@ParametersAreNonnullByDefault
@RequiredArgsConstructor
public class CompanyBranchServiceImpl implements CompanyBranchService {
    private final SecurityService securityService;
    private final CompanyBranchRepository companyBranchRepository;
    private final EstablishmentApi establishmentApi;

    @Override
    public void createCompanyBranch(CompanyCreateRequest companyCreateRequest) {
        Long createdEstablishmentId = establishmentApi.syncPostRequestWithBody(
            uriBuilder -> uriBuilder.path("/internal/establishment")
                .queryParam("ownerId", securityService.getLoggedInUser().getId())
                .build(),
            new ParameterizedTypeReference<>() {
            },
            Mono.just(companyCreateRequest),
            new ParameterizedTypeReference<>() {
            }
        );
        if (createdEstablishmentId != null) {
            companyBranchRepository.save(
                new Company().setId(createdEstablishmentId).setBusinessUser(securityService.getLoggedInUser())
            );
        }

    }

    @Override
    public List<ResponseShortEstablishmentInfo> getEstablishmentsByOwner(@Nullable String name) {
        BusinessUser user = securityService.getLoggedInUser();
        return establishmentApi.syncListGetWithParams(
            uriBuilder -> uriBuilder.path("/internal/establishment/owner")
                .queryParam("ownerId", user.getId())
                .queryParamIfPresent("name", Optional.ofNullable(name))
                .build(),
            new ParameterizedTypeReference<>() {
            }
        );
    }
}
