package ru.nsu.fit.directors.businessservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public record BusinessUserRegisterRequest(
    @NotNull(message = "Имя не может быть не указано.")
    @Pattern(regexp = "\\w+ \\w+ \\w+", message = "Имя должно состоять из трех слов.")
    String name,
    @NotNull(message = "Электронная почта не может быть не указана.")
    @Pattern(regexp = "\\w+@\\w+.\\w", message = "Электронная почта не удовлетворяет условиям.")
    String email,
    @NotNull(message = "Номер телефона не может быть не указан.")
    String phoneNumber
) {
}
