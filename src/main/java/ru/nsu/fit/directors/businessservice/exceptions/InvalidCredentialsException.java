package ru.nsu.fit.directors.businessservice.exceptions;

public class InvalidCredentialsException extends BaseException {
    public InvalidCredentialsException() {
        super("Данные для входа были указаны неверно.", "InvalidCredentialsException");
    }
}
