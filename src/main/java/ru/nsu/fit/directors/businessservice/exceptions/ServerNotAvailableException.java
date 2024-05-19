package ru.nsu.fit.directors.businessservice.exceptions;

public class ServerNotAvailableException extends BaseException {
    public ServerNotAvailableException() {
        super("Сервер в данный момент недоступен. Попробуйте позже.");
    }
}
