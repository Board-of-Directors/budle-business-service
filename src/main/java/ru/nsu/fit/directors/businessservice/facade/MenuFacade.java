package ru.nsu.fit.directors.businessservice.facade;

import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.businessservice.dto.request.RequestCategoryDto;
import ru.nsu.fit.directors.businessservice.dto.request.RequestProductDto;

@ParametersAreNonnullByDefault
public interface MenuFacade {
    /**
     * Добавить категорию в меню.
     *
     * @param requestCategoryDto информация о категории
     */
    void addCategory(RequestCategoryDto requestCategoryDto);

    /**
     * Удалить категорию из меню.
     *
     * @param establishmentId идентификатор заведения
     * @param categoryId      идентификатор категории
     */
    void deleteCategory(Long establishmentId, Long categoryId);

    /**
     * Добавить продукт в меню.
     *
     * @param requestProductDto информация о продукте
     */
    void addProduct(RequestProductDto requestProductDto);

    /**
     * Удалить продукт из меню.
     *
     * @param establishmentId идентификатор заведения
     * @param productId       идентификатор продукта
     */
    void deleteProduct(Long establishmentId, Long productId);
}
