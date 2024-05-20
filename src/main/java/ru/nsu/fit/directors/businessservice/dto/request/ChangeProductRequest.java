package ru.nsu.fit.directors.businessservice.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChangeProductRequest(
    @NotNull(message = "Идентификатор продукта не может быть не указан.")
    Long productId,
    String name,
    String price,
    String weightG,
    String description,
    Boolean isOnSale
) {
}
