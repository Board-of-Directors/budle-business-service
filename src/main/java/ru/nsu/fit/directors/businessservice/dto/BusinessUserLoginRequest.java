package ru.nsu.fit.directors.businessservice.dto;

import jakarta.validation.constraints.NotNull;

public record BusinessUserLoginRequest(
    @NotNull(message = "Логин не может быть не указан.")
    String login,
    @NotNull(message = "Пароль не может быть не указан.")
    String password
) {
}
