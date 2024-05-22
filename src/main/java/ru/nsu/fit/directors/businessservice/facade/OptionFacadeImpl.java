package ru.nsu.fit.directors.businessservice.facade;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.dto.ChangeOptionRequest;
import ru.nsu.fit.directors.businessservice.dto.request.RequestOptionDto;
import ru.nsu.fit.directors.businessservice.dto.response.AvailableOptionResponse;
import ru.nsu.fit.directors.businessservice.mapper.OptionConverter;
import ru.nsu.fit.directors.businessservice.model.AvailableOption;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.model.Option;
import ru.nsu.fit.directors.businessservice.service.BusinessUserService;
import ru.nsu.fit.directors.businessservice.service.CompanyService;
import ru.nsu.fit.directors.businessservice.service.OptionService;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class OptionFacadeImpl implements OptionFacade {
    private final OptionService optionService;
    private final BusinessUserService businessUserService;
    private final OptionConverter optionConverter;
    private final CompanyService companyService;

    @Nonnull
    @Override
    public List<AvailableOptionResponse> getAvailableOptions(Long workerId, Long establishmentId) {
        BusinessUser worker = businessUserService.getById(workerId);
        Company company = companyService.getById(establishmentId);
        Set<Option> availableOptions = optionService.getByWorker(worker, company).stream()
            .map(AvailableOption::getOption)
            .collect(Collectors.toSet());

        return Arrays.stream(Option.values())
            .map(option -> optionConverter.toResponse(option, availableOptions.contains(option)))
            .toList();
    }

    @Nonnull
    @Override
    public List<AvailableOptionResponse> getAllOptions() {
        return Arrays.stream(Option.values())
            .map(option -> optionConverter.toResponse(option, false))
            .toList();
    }

    @Override
    public void changeOptions(ChangeOptionRequest changeOptionRequest) {
        BusinessUser businessUser = businessUserService.getById(changeOptionRequest.workerId());
        Company company = companyService.getById(changeOptionRequest.establishmentId());
        List<AvailableOption> actualOptions = changeOptionRequest.options()
            .stream()
            .filter(RequestOptionDto::isEnabled)
            .map(option -> optionConverter.toModel(option, businessUser, company))
            .toList();
        optionService.replaceOptions(actualOptions, businessUser, company);
    }
}
