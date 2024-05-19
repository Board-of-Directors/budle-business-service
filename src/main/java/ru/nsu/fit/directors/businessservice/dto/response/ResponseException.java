package ru.nsu.fit.directors.businessservice.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseException {
    private String message;

    public ResponseException(String message) {
        this.message = message;
    }

}
