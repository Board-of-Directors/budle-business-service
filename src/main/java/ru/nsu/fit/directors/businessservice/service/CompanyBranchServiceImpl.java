package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.api.EstablishmentServiceClient;
import ru.nsu.fit.directors.businessservice.dto.request.CompanyCreateRequest;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseShortEstablishmentInfo;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.repository.CompanyBranchRepository;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;

@Service
@ParametersAreNonnullByDefault
@RequiredArgsConstructor
public class CompanyBranchServiceImpl implements CompanyBranchService {
    private final SecurityService securityService;
    private final CompanyBranchRepository companyBranchRepository;
    private final EstablishmentServiceClient establishmentClient;

    @Override
    public void createCompanyBranch(CompanyCreateRequest companyCreateRequest) {
        Long createdEstablishmentId =
            establishmentClient.createEstablishment(securityService.getLoggedInUser().getId());
        if (createdEstablishmentId != null) {
            companyBranchRepository.save(
                new Company().setId(createdEstablishmentId).setBusinessUser(securityService.getLoggedInUser())
            );
        }

    }

    @Override
    public List<ResponseShortEstablishmentInfo> getEstablishmentsByOwner(@Nullable String name) {
        BusinessUser user = securityService.getLoggedInUser();
        return establishmentClient.getEstablishmentsByOwner(user.getId());
    }
}
