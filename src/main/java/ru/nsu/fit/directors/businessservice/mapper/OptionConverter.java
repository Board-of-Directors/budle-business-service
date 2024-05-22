package ru.nsu.fit.directors.businessservice.mapper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.dto.request.RequestOptionDto;
import ru.nsu.fit.directors.businessservice.dto.response.AvailableOptionResponse;
import ru.nsu.fit.directors.businessservice.model.AvailableOption;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.model.Option;
import ru.nsu.fit.directors.businessservice.utils.EnumUtils;

@Component
@ParametersAreNonnullByDefault
public class OptionConverter {

    @Nonnull
    public AvailableOptionResponse toResponse(Option option, boolean isAvailable) {
        return AvailableOptionResponse.builder()
            .optionName(option.getReadableName())
            .isAvailable(isAvailable)
            .build();
    }

    @Nonnull
    public AvailableOption toModel(RequestOptionDto requestOptionDto, BusinessUser businessUser, Company company) {
        return new AvailableOption()
            .setOption(EnumUtils.findEnum(requestOptionDto.option(), Option.class))
            .setCompany(company)
            .setBusinessUser(businessUser);
    }
}
