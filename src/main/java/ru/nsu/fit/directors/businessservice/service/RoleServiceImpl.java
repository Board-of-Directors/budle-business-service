package ru.nsu.fit.directors.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.model.Company;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final SecurityService securityService;

    @Override
    public boolean isUserOwner(Long establishmentId) {
        return isInCompanies(
            securityService.getLoggedInUser().getCompanies(),
            establishmentId
        );
    }

    @Override
    public boolean isUserWorker(Long userId, Long establishmentId) {
        return isInCompanies(
            securityService.getLoggedInUser().getWorkerInCompanies(),
            establishmentId
        );
    }

    private boolean isInCompanies(List<Company> companies, Long establishmentId) {
        return companies.stream()
            .anyMatch(company -> company.getId().equals(establishmentId));
    }
}
