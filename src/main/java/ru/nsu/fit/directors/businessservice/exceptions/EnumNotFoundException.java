package ru.nsu.fit.directors.businessservice.exceptions;

public class EnumNotFoundException extends BaseException {
    public <E extends Enum<E>> EnumNotFoundException(Class<E> enumClass, String name) {
        super("Отсутствует константа %s с названием %s".formatted(enumClass.getSimpleName(), name));
    }
}
