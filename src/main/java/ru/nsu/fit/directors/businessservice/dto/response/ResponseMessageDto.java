package ru.nsu.fit.directors.businessservice.dto.response;

import java.time.LocalDateTime;

public record ResponseMessageDto(String message, LocalDateTime timestamp, String senderType) {
}
