package ru.nsu.fit.directors.businessservice.exceptions;

public class NotEnoughRightException extends BaseException {
    public NotEnoughRightException() {
        super("Не хватает прав для совершения данной операции.");
    }
}
