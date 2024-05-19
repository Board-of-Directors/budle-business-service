package ru.nsu.fit.directors.businessservice.exceptions;

public class WrongNameFormatException extends BaseException {
    public WrongNameFormatException() {
        super("Полное имя пользователя должно быть в формате 'Фамилия Имя Отчество'");
    }
}
