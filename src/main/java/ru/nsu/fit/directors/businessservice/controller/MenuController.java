package ru.nsu.fit.directors.businessservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.businessservice.dto.request.ChangeCategoryRequest;
import ru.nsu.fit.directors.businessservice.dto.request.ChangeProductRequest;
import ru.nsu.fit.directors.businessservice.dto.request.RequestCategoryDto;
import ru.nsu.fit.directors.businessservice.dto.request.RequestProductDto;
import ru.nsu.fit.directors.businessservice.facade.MenuFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/business/menu")
public class MenuController {
    private final MenuFacade menuFacade;

    @Operation(description = "Добавить категорию в меню")
    @PostMapping
    public void addCategory(@RequestBody RequestCategoryDto requestCategoryDto) {
        menuFacade.addCategory(requestCategoryDto);
    }

    @Operation(description = "Удалить категорию из меню")
    @DeleteMapping
    public void deleteCategory(@RequestParam Long establishmentId, @RequestParam Long categoryId) {
        menuFacade.deleteCategory(establishmentId, categoryId);
    }

    @Operation(description = "Редактировать категорию")
    @PutMapping
    public void changeCategory(@RequestBody @Valid ChangeCategoryRequest changeCategoryRequest){
        menuFacade.changeCategory(changeCategoryRequest);
    }

    @Operation(description = "Добавить товар в меню")
    @PostMapping(value = "/product")
    public void addProduct(@RequestBody RequestProductDto requestProductDto) {
        menuFacade.addProduct(requestProductDto);
    }

    @Operation(description = "Удалить товар из меню")
    @DeleteMapping(value = "/product")
    public void deleteProduct(@RequestParam Long establishmentId, @RequestParam Long productId) {
        menuFacade.deleteProduct(establishmentId, productId);
    }

    @Operation(description = "Изменение продукта")
    @PutMapping(value = "/product")
    public void changeProduct(@RequestBody @Valid ChangeProductRequest changeProductRequest){
        menuFacade.changeProduct(changeProductRequest);
    }
}
