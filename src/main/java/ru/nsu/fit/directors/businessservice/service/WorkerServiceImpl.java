package ru.nsu.fit.directors.businessservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseWorkerDto;
import ru.nsu.fit.directors.businessservice.mapper.BusinessUserMapper;
import ru.nsu.fit.directors.businessservice.model.AvailableOption;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.repository.AvailableOptionRepository;
import ru.nsu.fit.directors.businessservice.repository.BusinessUserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Service
@Transactional
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

}
