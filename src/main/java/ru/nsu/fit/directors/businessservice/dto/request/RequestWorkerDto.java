package ru.nsu.fit.directors.businessservice.dto.request;

public record RequestWorkerDto(
    Long establishmentId,
    String name,
    String email
) {
}
