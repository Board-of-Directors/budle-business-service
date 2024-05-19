package ru.nsu.fit.directors.businessservice.dto.response;

import lombok.Builder;

@Builder
public record BusinessUserCredentialsResponse(
    Long id,
    String firstName,
    String middleName,
    String lastName,
    String phoneNumber,
    String email,
    String login
) {
}
