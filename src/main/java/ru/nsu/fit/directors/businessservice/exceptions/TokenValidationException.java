package ru.nsu.fit.directors.businessservice.exceptions;

public class TokenValidationException extends BaseException {
    public TokenValidationException(String message, String type) {
        super(message, type);
    }
}
