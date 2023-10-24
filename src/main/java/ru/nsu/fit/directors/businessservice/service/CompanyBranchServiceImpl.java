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

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

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
            uriBuilder -> uriBuilder.path("/establishment")
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
    public List<ResponseShortEstablishmentInfo> getEstablishmentsByOwner() {
        BusinessUser user = securityService.getLoggedInUser();
        return establishmentApi.syncListGetWithParams(
            uriBuilder -> uriBuilder.path("/establishment/owner")
                .queryParam("ownerId", user.getId())
                .build(),
            new ParameterizedTypeReference<>() {
            }
        );
    }
}
