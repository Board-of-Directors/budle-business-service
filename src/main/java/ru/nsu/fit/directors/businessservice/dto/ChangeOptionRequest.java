package ru.nsu.fit.directors.businessservice.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import ru.nsu.fit.directors.businessservice.dto.request.RequestOptionDto;

public record ChangeOptionRequest(
    @NotNull(message = "Идентификатор не может отсутствовать")
    Long workerId,
    @NotNull(message = "Идентификатор заведения не может отсутствовать")
    Long establishmentId,
    @NotNull(message = "Список опций не может отсутствовать")
    List<RequestOptionDto> options
) {
}
