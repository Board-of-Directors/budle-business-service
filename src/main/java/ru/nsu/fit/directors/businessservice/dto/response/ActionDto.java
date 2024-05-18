package ru.nsu.fit.directors.businessservice.dto.response;

public record ActionDto(
    String actionName,
    Integer nextStatus
) {
}
