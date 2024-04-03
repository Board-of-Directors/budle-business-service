package ru.nsu.fit.directors.businessservice.event;

public record UserMessageEvent(Long userId, Long orderId, String message) {
}
