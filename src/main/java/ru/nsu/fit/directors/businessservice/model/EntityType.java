package ru.nsu.fit.directors.businessservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EntityType {
    COMPANY("Заведение"),
    BUSINESS_USER("Работник"),
    ;

    private final String readableName;

}
