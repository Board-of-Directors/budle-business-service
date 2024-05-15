package ru.nsu.fit.directors.businessservice.dto.request;

public record RequestProductDto(
    String name,
    String price,
    String weightG,
    String description,
    Long establishmentId,
    Long categoryId,
    boolean isOnSale
) {

}
