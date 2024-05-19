package ru.nsu.fit.directors.businessservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Option {
    ADD_MENU_POSITION("Добавление позиции в меню"),
    DELETE_MENU_POSITION("Удаление позиции из меню"),
    ADD_WORKERS("Добавление работников заведения"),
    DELETE_WORKERS("Удаление работников заведения"),
    SEARCHING_WORKERS("Просмотр работников заведения"),
    DELETE_COMPANY("Удаление заведения"),
    EDITING_COMPANY("Редактирование информации заведения"),
    SEARCHING_ORDERS("Просмотр заказов заведения"),
    CHANGING_ORDER_STATUSES("Изменения статусов заказов"),
    ;

    private final String readableName;
}
