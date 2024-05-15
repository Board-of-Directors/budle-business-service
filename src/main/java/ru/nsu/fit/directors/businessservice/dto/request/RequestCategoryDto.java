package ru.nsu.fit.directors.businessservice.dto.request;

public record RequestCategoryDto(
    String name,
    Long parentCategoryId,
    Long establishmentId
) {

}

