package ru.nsu.fit.directors.businessservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;
import java.util.List;

public record RequestWorkingHoursDto(
    @NotNull(message = "Дни недели не могут быть не заданы")
    @Size(min = 1, message = "Список дней не может быть пустым")
    List<String> days,
    @NotNull(message = "Время начала работы не может быть не задано.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    LocalTime startTime,
    @NotNull(message = "Время окончания работы не может быть не задано.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    LocalTime endTime
) {
}
