package ru.nsu.fit.directors.businessservice.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class, that used for performing exception in the web response.
 * Contains error message and error type.
 */
@Getter
@Setter
@NoArgsConstructor
public class ResponseException {
    private String message;

    /**
     * Default constructor of exception.
     *
     * @param message of the error.
     */
    public ResponseException(String message) {
        this.message = message;
    }

}
