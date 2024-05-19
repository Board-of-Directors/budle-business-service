package ru.nsu.fit.directors.businessservice.exceptions;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException() {
        super("Не авторизован");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
