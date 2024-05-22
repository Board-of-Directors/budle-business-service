package ru.nsu.fit.directors.businessservice.controller;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import ru.nsu.fit.directors.businessservice.dto.request.RequestOptionDto;

public record AddWorkerRequest(
    @NotNull(message = "Идентификатор работника не может отсутствовать")
    Long workerId,
    @NotNull(message = "Идентификатор заведения не может отсутствовать")
    Long establishmentId,
    @NotNull(message = "Набор доступных опций не может отсутствовать")
    List<RequestOptionDto> options
) {
}
