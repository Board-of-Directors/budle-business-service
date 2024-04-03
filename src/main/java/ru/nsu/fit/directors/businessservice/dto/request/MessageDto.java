package ru.nsu.fit.directors.businessservice.dto.request;

import java.time.LocalDateTime;

public record MessageDto(String message, LocalDateTime timestamp) {
}
