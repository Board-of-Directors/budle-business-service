package ru.nsu.fit.directors.businessservice.exceptions;

public class NotEnoughRightException extends BaseException {
    public NotEnoughRightException() {
        super("Not enough rights for this operation", "NotEnoughRightException");
    }
}
