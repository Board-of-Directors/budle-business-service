package ru.nsu.fit.directors.businessservice.utils;

import java.util.Arrays;

import javax.annotation.Nonnull;

import lombok.experimental.UtilityClass;
import ru.nsu.fit.directors.businessservice.exceptions.EnumNotFoundException;

@UtilityClass
public class EnumUtils {
    @Nonnull
    public <E extends Enum<E>> E findEnum(String name, Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
            .filter(parseableEnum -> parseableEnum.name().equalsIgnoreCase(name))
            .findFirst()
            .orElseThrow(() -> new EnumNotFoundException(enumClass, name));
    }
}
