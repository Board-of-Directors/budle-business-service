package ru.nsu.fit.directors.businessservice.facade;

import java.util.List;
import java.util.Objects;

import javax.annotation.ParametersAreNonnullByDefault;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.controller.AddWorkerRequest;
import ru.nsu.fit.directors.businessservice.dto.request.InviteWorkerRequest;
import ru.nsu.fit.directors.businessservice.dto.request.RequestOptionDto;
import ru.nsu.fit.directors.businessservice.exceptions.WrongActionException;
import ru.nsu.fit.directors.businessservice.mapper.OptionConverter;
import ru.nsu.fit.directors.businessservice.model.AvailableOption;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.model.Option;
import ru.nsu.fit.directors.businessservice.service.BusinessUserService;
import ru.nsu.fit.directors.businessservice.service.CompanyService;
import ru.nsu.fit.directors.businessservice.service.EmployeeService;
import ru.nsu.fit.directors.businessservice.service.OptionService;

@Component
@Transactional
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class WorkerFacadeImpl implements WorkerFacade {
    private final EmployeeService employeeService;
    private final OptionService optionService;
    private final BusinessUserService businessUserService;
    private final CompanyService companyService;
    private final OptionConverter optionConverter;

    @Override
    public void deleteWorker(Long establishmentId, Long workerId) {
        Company company = companyService.getById(establishmentId);
        employeeService.validateWorker(company, Option.DELETE_WORKERS);
        BusinessUser worker = businessUserService.getById(workerId);
        if (Objects.equals(worker.getId(), company.getBusinessUser().getId())) {
            throw new WrongActionException("Нельзя удалить владельца из заведения");
        }
        optionService.delete(worker, company);
    }

    @Override
    public void inviteWorker(InviteWorkerRequest inviteWorkerRequest) {
        employeeService.validateOwner(inviteWorkerRequest.establishmentId());
        BusinessUser businessUser = businessUserService.getByToken(inviteWorkerRequest.token());
        Company company = companyService.getById(inviteWorkerRequest.establishmentId());
        if (Objects.equals(businessUser.getId(), company.getBusinessUser().getId())) {
            throw new WrongActionException("Нельзя добавить владельца заведения");
        }
        List<AvailableOption> availableOptions = inviteWorkerRequest.options()
            .stream()
            .filter(RequestOptionDto::isEnabled)
            .map(option -> optionConverter.toModel(option, businessUser, company))
            .toList();
        optionService.addInitialOptions(businessUser, company, availableOptions);
    }

    @Override
    public void addWorker(AddWorkerRequest addWorkerRequest) {
        employeeService.validateOwner(addWorkerRequest.establishmentId());
        BusinessUser businessUser = businessUserService.getById(addWorkerRequest.workerId());
        Company company = companyService.getById(addWorkerRequest.establishmentId());
        if (Objects.equals(businessUser.getId(), company.getBusinessUser().getId())) {
            throw new WrongActionException("Нельзя добавить владельца заведения");
        }
        List<AvailableOption> availableOptions = addWorkerRequest.options()
            .stream()
            .filter(RequestOptionDto::isEnabled)
            .map(option -> optionConverter.toModel(option, businessUser, company))
            .toList();
        optionService.addInitialOptions(businessUser, company, availableOptions);
    }
}
