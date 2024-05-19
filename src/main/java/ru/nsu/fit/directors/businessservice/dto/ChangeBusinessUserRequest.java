package ru.nsu.fit.directors.businessservice.dto;

import javax.annotation.Nullable;

public record ChangeBusinessUserRequest(
    @Nullable
    String fullName,
    @Nullable
    String phoneNumber,
    @Nullable
    String email
) {
}
