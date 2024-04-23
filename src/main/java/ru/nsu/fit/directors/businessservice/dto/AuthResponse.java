package ru.nsu.fit.directors.businessservice.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
    String accessToken,
    String refreshToken
) {
}
