package ru.nsu.fit.directors.businessservice.service;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.model.AvailableOption;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.model.Option;
import ru.nsu.fit.directors.businessservice.repository.AvailableOptionRepository;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class OptionServiceImpl implements OptionService {
    private final AvailableOptionRepository availableOptionRepository;

    @Nonnull
    @Override
    public List<AvailableOption> getByWorker(BusinessUser worker, Company company) {
        return availableOptionRepository.findAllByBusinessUserAndCompany(worker, company);
    }

    @Override
    public void replaceOptions(List<AvailableOption> actualOptions, BusinessUser businessUser, Company company) {
        availableOptionRepository.deleteAllByBusinessUserAndCompany(businessUser, company);
        availableOptionRepository.saveAll(actualOptions);
    }

    @Override
    public void addInitialOptions(BusinessUser businessUser, Company company, List<AvailableOption> options) {
        availableOptionRepository.save(
            new AvailableOption()
                .setBusinessUser(businessUser)
                .setCompany(company)
                .setOption(Option.VIEW_COMPANY_INFORMATION)
        );
        availableOptionRepository.saveAll(options);
    }

    @Override
    public void delete(BusinessUser worker, Company company) {
        availableOptionRepository.deleteAllByBusinessUserAndCompany(worker, company);
    }
}
