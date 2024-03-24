package ru.nsu.fit.directors.businessservice.dto;

public record BusinessOrderNotificationEvent(Long businessId, Long orderId, String message) {
}
