package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.dto.request.RequestWorkerDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseWorkerDto;
import ru.nsu.fit.directors.businessservice.mapper.BusinessUserMapper;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.repository.BusinessUserRepository;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class WorkerServiceImpl implements WorkerService {
    private final EmployeeService employeeService;
    private final BusinessUserMapper businessUserMapper;
    private final BusinessUserRepository businessUserRepository;
    private final MailNotificationService notificationService;
    private final CompanyService companyService;

    @Override
    public void createWorker(RequestWorkerDto workerDto) {
        Company company = companyService.getById(workerDto.establishmentId());
        employeeService.validateWorker(workerDto.establishmentId(), false);
        String password = RandomStringUtils.random(12, true, true);
        BusinessUser businessUser = businessUserMapper.toBusinessUser(workerDto, password);
        businessUserRepository.save(businessUser);
        notificationService.sendRegistrationNotification(businessUser, password);
        company.getWorkers().add(businessUser);
        companyService.save(company);
    }

    @Override
    public void deleteWorker(Long establishmentId, Long workerId) {
        Company company = companyService.getById(establishmentId);
        employeeService.validateWorker(establishmentId, false);
        BusinessUser worker = businessUserRepository.findById(workerId).orElseThrow();
        company.getWorkers().remove(worker);
        companyService.save(company);
    }

    @Nonnull
    @Override
    public List<ResponseWorkerDto> searchWorkers(Long establishmentId) {
        employeeService.validateWorker(establishmentId, true);
        return companyService.getById(establishmentId).getWorkers()
            .stream()
            .map(businessUserMapper::toWorkerDto)
            .toList();

    }

    @Override
    public void addWorker(Long workerId, Long establishmentId) {
        employeeService.validateWorker(establishmentId, false);
        BusinessUser worker = businessUserRepository.findById(workerId).orElseThrow();
        Company company = companyService.getById(establishmentId);
        company.getWorkers().add(worker);
        companyService.save(company);
    }
}
