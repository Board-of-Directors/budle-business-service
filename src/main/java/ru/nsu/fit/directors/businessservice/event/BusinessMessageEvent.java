package ru.nsu.fit.directors.businessservice.event;

public record BusinessMessageEvent(Long userId, Long orderId, String message) {
}
