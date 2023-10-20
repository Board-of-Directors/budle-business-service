package ru.nsu.fit.directors.businessservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CompanyCreateRequest(
    @NotNull(message = "Имя не может быть не задано.")
    @Size(max = 200, message = "Название заведения не может превышать 200 символов.")
    String name,
    @NotNull(message = "Категория не может быть не задана.")
    String category,
    @NotNull(message = "Информация о тэгах заведения не может быть не задана.")
    List<RequestTagDto> tags,
    @NotNull(message = "Основное изображение не может быть не задано.")
    String image,
    @NotNull(message = "Описание не может быть не задано.")
    @Size(max = 1000, message = "Описание не может быть длиннее 1000 символов.")
    String description,
    @NotNull(message = "Информация об адресе не может быть не задана.")
    @Size(max = 200, message = "Адрес не может быть длиннее 200 символов.")
    String address,
    String subway,
    Integer price,
    Float rating,
    @NotNull(message = "Информация о рабочих часах заведения не может быть не задано.")
    @Size(min = 1, max = 7, message = "Дней работы не может быть меньше 1 и больше 7")
    @Valid
    List<RequestWorkingHoursDto> workingHours,
    @NotNull(message = "Информация о фотографиях заведения не может быть пустой.")
    List<RequestPhotoDto> photosInput,
    String map,
    String cuisineCountry,
    String starsCount
) {
}
