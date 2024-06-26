package ru.nsu.fit.directors.businessservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.api.EstablishmentServiceClient;
import ru.nsu.fit.directors.businessservice.dto.request.CompanyCreateRequestV2;
import ru.nsu.fit.directors.businessservice.dto.response.BaseResponse;
import ru.nsu.fit.directors.businessservice.dto.response.CompanyDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseShortEstablishmentInfo;
import ru.nsu.fit.directors.businessservice.exceptions.EntityNotFoundException;
import ru.nsu.fit.directors.businessservice.model.AvailableOption;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.model.EntityType;
import ru.nsu.fit.directors.businessservice.model.Option;
import ru.nsu.fit.directors.businessservice.repository.AvailableOptionRepository;
import ru.nsu.fit.directors.businessservice.repository.CompanyRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class CompanyServiceImpl implements CompanyService {
    private final EmployeeService employeeService;
    private final CompanyRepository companyRepository;
    private final EstablishmentServiceClient establishmentClient;
    private final AvailableOptionRepository availableOptionRepository;

    @Override
    public void createCompanyBranch(CompanyCreateRequestV2 createRequest) {
        BusinessUser businessUser = employeeService.getLoggedInUser();
        Optional.ofNullable(establishmentClient.createEstablishmentV2(businessUser.getId(), createRequest))
            .map(ResponseEntity::getBody)
            .ifPresent(id -> create(id, businessUser));
    }

    private void create(BaseResponse<Long> createdEstablishmentId, BusinessUser businessUser) {
        log.info("Created establishment id {}", createdEstablishmentId.getResult());
        Company company = companyRepository.save(
            new Company()
                .setId(createdEstablishmentId.getResult())
                .setBusinessUser(businessUser)
        );
        var availableOptions = Arrays.stream(Option.values())
            .map(option -> new AvailableOption().setCompany(company).setBusinessUser(businessUser).setOption(option))
            .toList();
        availableOptionRepository.saveAll(availableOptions);
    }

    @Nonnull
    @Override
    public List<ResponseShortEstablishmentInfo> getEstablishmentsByOwner(@Nullable String name) {
        BusinessUser user = employeeService.getLoggedInUser();
        return Optional.ofNullable(establishmentClient.getEstablishmentsByOwner(user.getId(), name))
            .map(ResponseEntity::getBody)
            .map(BaseResponse::getResult)
            .orElseGet(List::of);
    }

    @Override
    public void deleteCompany(Long establishmentId) {
        Company company = getById(establishmentId);
        employeeService.validateWorker(company, Option.DELETE_COMPANY);
        availableOptionRepository.deleteAllByCompany(company);
        companyRepository.deleteById(establishmentId);
        establishmentClient.deleteEstablishment(establishmentId);
    }

    @Override
    public void changeCompany(Long establishmentId, CompanyCreateRequestV2 changeRequest) {
        Company company = getById(establishmentId);
        employeeService.validateWorker(company, Option.EDITING_COMPANY);
        establishmentClient.update(establishmentId, changeRequest);
    }

    @Nonnull
    @Override
    public Company getById(Long id) {
        return companyRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(EntityType.COMPANY, id));
    }

    @Override
    public void save(Company company) {
        companyRepository.save(company);
    }

    @Nonnull
    @Override
    public List<CompanyDto> getEstablishments(@Nullable String name) {
        BusinessUser user = employeeService.getLoggedInUser();
        List<Long> companyIds = StreamEx.of(availableOptionRepository.findByBusinessUser(user))
            .map(AvailableOption::getCompany)
            .map(Company::getId)
            .distinct()
            .toList();
        List<CompanyDto> result = Optional.ofNullable(establishmentClient.getCompaniesByIds(companyIds).getBody())
            .map(BaseResponse::getResult)
            .orElseGet(List::of);
        if (name != null) {
            return result.stream()
                .filter(company -> StringUtils.containsIgnoreCase(company.name(), name))
                .toList();
        }
        return result;

    }
}
