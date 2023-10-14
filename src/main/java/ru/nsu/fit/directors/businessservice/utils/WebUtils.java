package ru.nsu.fit.directors.businessservice.utils;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class WebUtils {
    public Map<String, List<String>> toQuery(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
            .peek(field -> field.setAccessible(true))
            .filter(field -> getFieldFromObject(field, object) != null)
            .collect(Collectors.toMap(Field::getName, field ->
                List.of(String.valueOf(getFieldFromObject(field, object)))
            ));

    }

    private Object getFieldFromObject(Field field, Object object) {
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
