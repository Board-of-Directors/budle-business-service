package ru.nsu.fit.directors.businessservice.exceptions;

public class ClientException extends BaseException {
    public ClientException(String message) {
        super(message, "ClientException");
    }
}
