package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nsu.fit.directors.businessservice.api.EstablishmentServiceClient;
import ru.nsu.fit.directors.businessservice.dto.request.CompanyCreateRequest;
import ru.nsu.fit.directors.businessservice.dto.response.BaseResponse;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseShortEstablishmentInfo;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.repository.CompanyBranchRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class CompanyBranchServiceImpl implements CompanyBranchService {
    private final SecurityService securityService;
    private final CompanyBranchRepository companyBranchRepository;
    private final EstablishmentServiceClient establishmentClient;

    @Override
    public void createCompanyBranch(CompanyCreateRequest companyCreateRequest) {
        BaseResponse<Long> createdEstablishmentId =
            establishmentClient.createEstablishment(securityService.getLoggedInUser().getId(), companyCreateRequest)
                .getBody();
        if (createdEstablishmentId != null) {
            log.info("Created establishment id {}", createdEstablishmentId.getResult());
            companyBranchRepository.save(
                new Company()
                    .setId(createdEstablishmentId.getResult())
                    .setBusinessUser(securityService.getLoggedInUser())
            );
        }

    }

    @Override
    public void createCompanyBranch(CompanyCreateRequest companyCreateRequest, MultipartFile[] images) {
        BaseResponse<Long> createdEstablishmentId =
            establishmentClient.createEstablishmentV2(
                    securityService.getLoggedInUser().getId(),
                    companyCreateRequest,
                    images
                )
                .getBody();
        if (createdEstablishmentId != null) {
            log.info("Created establishment id {}", createdEstablishmentId.getResult());
            companyBranchRepository.save(
                new Company()
                    .setId(createdEstablishmentId.getResult())
                    .setBusinessUser(securityService.getLoggedInUser())
            );
        }
    }

    @Nonnull
    @Override
    public List<ResponseShortEstablishmentInfo> getEstablishmentsByOwner(@Nullable String name) {
        BusinessUser user = securityService.getLoggedInUser();
        return establishmentClient.getEstablishmentsByOwner(user.getId()).getBody().getResult();
    }
}
