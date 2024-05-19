package ru.nsu.fit.directors.businessservice.exceptions;

public class AlreadyRegisteredException extends BaseException {
    public AlreadyRegisteredException() {
        super("Бизнес пользователь с такими данными уже существует.");
    }
}
