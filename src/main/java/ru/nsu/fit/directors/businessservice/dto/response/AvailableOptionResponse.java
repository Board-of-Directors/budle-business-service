package ru.nsu.fit.directors.businessservice.dto.response;

import lombok.Builder;

@Builder
public record AvailableOptionResponse(
    String optionName,
    Boolean isAvailable
) {
}
