package ru.nsu.fit.directors.businessservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAnswerRequest(
    @NotNull(message = "Идентификатор отзыва не может отсутствовать.")
    Long reviewId,
    @NotBlank(message = "Текст ответа не может быть пустым.")
    String answer
) {
}
