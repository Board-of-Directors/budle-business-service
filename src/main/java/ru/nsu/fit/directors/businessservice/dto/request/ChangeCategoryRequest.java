package ru.nsu.fit.directors.businessservice.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChangeCategoryRequest(
    @NotNull(message = "Идентификатор категории не может быть не указан.")
    Long id,
    Long establishmentId,
    String name
) {
}
