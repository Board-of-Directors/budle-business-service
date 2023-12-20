package ru.nsu.fit.directors.businessservice.dto.response;

import lombok.Builder;

@Builder
public record ResponseWorkerDto(
    Long id,
    String firstName,
    String middleName,
    String lastName
) {
}
