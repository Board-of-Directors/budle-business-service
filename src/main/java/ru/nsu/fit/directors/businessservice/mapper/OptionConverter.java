package ru.nsu.fit.directors.businessservice.mapper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.dto.response.AvailableOptionResponse;
import ru.nsu.fit.directors.businessservice.model.Option;

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
}
