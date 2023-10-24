package ru.nsu.fit.directors.businessservice.exceptions;

public class InvalidCredentialsException extends BaseException {
    public InvalidCredentialsException() {
        super("Your user credentials was invalid", "InvalidCredentialsException");
    }
}
