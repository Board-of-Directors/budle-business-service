package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.dto.request.RequestWorkerDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseWorkerDto;
import ru.nsu.fit.directors.businessservice.exceptions.NotEnoughRightException;
import ru.nsu.fit.directors.businessservice.mapper.BusinessUserMapper;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.repository.BusinessUserRepository;
import ru.nsu.fit.directors.businessservice.repository.CompanyBranchRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final EmployeeService employeeService;
    private final BusinessUserMapper businessUserMapper;
    private final BusinessUserRepository businessUserRepository;
    private final CompanyBranchRepository companyBranchRepository;
    private final MailNotificationService notificationService;

    @Override
    public void createWorker(RequestWorkerDto workerDto) {
        employeeService.validateWorker(workerDto.establishmentId(), false);
        String password = RandomStringUtils.random(12, true, true);
        BusinessUser businessUser = businessUserMapper.toBusinessUser(workerDto, password);
        businessUserRepository.save(businessUser);
        notificationService.sendRegistrationNotification(businessUser, password);
        Company company = companyBranchRepository.findById(workerDto.establishmentId()).orElseThrow();
        company.getWorkers().add(businessUser);
        companyBranchRepository.save(company);
    }

    @Override
    public void deleteWorker(Long establishmentId, Long workerId) {
        employeeService.validateWorker(establishmentId, false);
        BusinessUser worker = businessUserRepository.findById(workerId).orElseThrow();
        Company company = companyBranchRepository.findById(establishmentId).orElseThrow();
        company.getWorkers().remove(worker);
        companyBranchRepository.save(company);
    }

    @Override
    public List<ResponseWorkerDto> searchWorkers(Long establishmentId) {
        employeeService.validateWorker(establishmentId, true);
        return companyBranchRepository.findById(establishmentId)
            .orElseThrow()
            .getWorkers()
            .stream()
            .map(businessUserMapper::toWorkerDto)
            .toList();

    }

    @Override
    public void addWorker(Long workerId, Long establishmentId) {
        employeeService.validateWorker(establishmentId, false);
        BusinessUser worker = businessUserRepository.findById(workerId).orElseThrow();
        Company company = companyBranchRepository.findById(establishmentId).orElseThrow();
        company.getWorkers().add(worker);
        companyBranchRepository.save(company);
    }
}
