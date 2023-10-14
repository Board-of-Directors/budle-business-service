package ru.nsu.fit.directors.businessservice.exceptions;

public class UserNotLoggedInException extends BaseException {
    public UserNotLoggedInException() {
        super("Юзер не вошел в аккаунт.", "UserNotLoggedInException");
    }
}
