package ru.nsu.fit.directors.businessservice.service;

import javax.annotation.ParametersAreNonnullByDefault;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.exceptions.NotEnoughRightException;
import ru.nsu.fit.directors.businessservice.exceptions.UserNotLoggedInException;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.model.Option;
import ru.nsu.fit.directors.businessservice.repository.AvailableOptionRepository;
import ru.nsu.fit.directors.businessservice.repository.BusinessUserRepository;
import ru.nsu.fit.directors.businessservice.security.JwtTokenRepository;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class EmployeeServiceImpl implements EmployeeService {
    private final BusinessUserRepository businessUserRepository;
    private final JwtTokenRepository jwtTokenRepository;
    private final AvailableOptionRepository availableOptionRepository;

    @Override
    public void validateWorker(Long establishmentId, boolean canBeWorker) {
        BusinessUser loggedUser = getLoggedInUser();
        boolean isOwner = loggedUser.getCompanies().stream()
            .anyMatch(company -> company.getId().equals(establishmentId));
        boolean isWorker = loggedUser.getWorkerInCompanies().stream()
            .anyMatch(company -> company.getId().equals(establishmentId));
        if (!isOwner && (!canBeWorker || !isWorker)) {
            throw new NotEnoughRightException();
        }
    }

    @Override
    public void validateWorker(Company company, Option option) {
        BusinessUser user = getLoggedInUser();
        var availableOption = availableOptionRepository.findByBusinessUserAndCompanyAndOption(user, company, option);
        if (availableOption.isEmpty()) {
            throw new NotEnoughRightException();
        }
    }

    @Nonnull
    @Override
    public BusinessUser getLoggedInUser() {
        return businessUserRepository.findById(jwtTokenRepository.getUserIdOrThrow())
            .orElseThrow(UserNotLoggedInException::new);
    }
}
