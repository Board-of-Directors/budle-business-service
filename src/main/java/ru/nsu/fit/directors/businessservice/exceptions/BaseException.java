package ru.nsu.fit.directors.businessservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {
    private String type;

    public BaseException(String message, String type) {
        super(message);
        this.type = type;
    }
}
