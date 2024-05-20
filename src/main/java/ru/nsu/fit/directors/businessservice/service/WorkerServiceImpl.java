package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseWorkerDto;
import ru.nsu.fit.directors.businessservice.exceptions.EntityNotFoundException;
import ru.nsu.fit.directors.businessservice.mapper.BusinessUserMapper;
import ru.nsu.fit.directors.businessservice.model.AvailableOption;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.model.EntityType;
import ru.nsu.fit.directors.businessservice.model.Option;
import ru.nsu.fit.directors.businessservice.repository.AvailableOptionRepository;
import ru.nsu.fit.directors.businessservice.repository.BusinessUserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class WorkerServiceImpl implements WorkerService {
    private final EmployeeService employeeService;
    private final BusinessUserMapper businessUserMapper;
    private final BusinessUserRepository businessUserRepository;
    private final CompanyService companyService;
    private final AvailableOptionRepository availableOptionRepository;

    @Override
    public void deleteWorker(Long establishmentId, Long workerId) {
        Company company = companyService.getById(establishmentId);
        employeeService.validateOwner(establishmentId);
        BusinessUser worker = businessUserRepository.findById(workerId).orElseThrow();
        availableOptionRepository.deleteAllByBusinessUserAndCompany(worker, company);
    }

    @Nonnull
    @Override
    public List<ResponseWorkerDto> searchWorkers(Long establishmentId) {
        employeeService.validateOwner(establishmentId);
        Company company = companyService.getById(establishmentId);
        return StreamEx.of(availableOptionRepository.findAllByCompany(company))
            .map(AvailableOption::getBusinessUser)
            .distinct(BusinessUser::getId)
            .map(businessUserMapper::toWorkerDto)
            .toList();

    }

    @Override
    public void addWorker(Long workerId, Long establishmentId) {
        employeeService.validateOwner(establishmentId);
        BusinessUser worker = businessUserRepository.findById(workerId).orElseThrow();
        Company company = companyService.getById(establishmentId);
        addWorker(company, worker);
    }

    @Nonnull
    @Override
    public List<ResponseWorkerDto> getAllWorkers() {
        BusinessUser businessUser = employeeService.getLoggedInUser();
        return StreamEx.of(businessUser.getCompanies())
            .map(availableOptionRepository::findAllByCompany)
            .flatMap(Collection::stream)
            .map(AvailableOption::getBusinessUser)
            .distinct(BusinessUser::getId)
            .filter(user -> !Objects.equals(user.getId(), businessUser.getId()))
            .map(businessUserMapper::toWorkerDto)
            .toList();
    }

    @Override
    public void inviteWorker(Long establishmentId, String token) {
        BusinessUser businessUser = businessUserRepository.findByToken(UUID.fromString(token))
            .orElseThrow(() -> new EntityNotFoundException(EntityType.BUSINESS_USER, token));
        Company company = companyService.getById(establishmentId);
        addWorker(company, businessUser);

    }

    private void addWorker(Company company, BusinessUser businessUser) {
        availableOptionRepository.save(
            new AvailableOption()
                .setBusinessUser(businessUser)
                .setCompany(company)
                .setOption(Option.VIEW_COMPANY_INFORMATION)
        );
    }
}
