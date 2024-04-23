package ru.nsu.fit.directors.businessservice.exceptions;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException() {
        super("Не авторизован", "NOT_AUTHORIZED");
    }

    public UnauthorizedException(String message, String type) {
        super(message, type);
    }
}
