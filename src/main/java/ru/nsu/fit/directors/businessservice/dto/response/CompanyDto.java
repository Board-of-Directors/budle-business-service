package ru.nsu.fit.directors.businessservice.dto.response;

public record CompanyDto(
    Float rating,
    String address,
    String image,
    Boolean hasMap,
    Boolean hasCardPayment,
    String name,
    Long id,
    String category,
    String cuisineCountry,
    String starsCount
) {
}
