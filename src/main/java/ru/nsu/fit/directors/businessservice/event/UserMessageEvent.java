package ru.nsu.fit.directors.businessservice.event;

public record UserMessageEvent(Long id, Long orderId, String message) {
}
