package ru.nsu.fit.directors.businessservice.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record InviteWorkerRequest(
    @NotNull(message = "Идентификатор заведения не может отсутствовать")
    Long establishmentId,
    @NotNull(message = "Токен не может отсутствовать")
    String token,
    @NotNull(message = "Набор доступных опций не может отсутствовать")
    List<RequestOptionDto> options
) {
}
