package ru.nsu.fit.directors.businessservice.dto.request;

import jakarta.validation.constraints.NotNull;

public record RequestOptionDto(
    @NotNull(message = "Опция не может отсутствовать")
    String option,
    @NotNull(message = "Признак включенности не может отсутствовать")
    Boolean isEnabled
) {
}
