package ru.nsu.fit.directors.businessservice.dto.response;

import javax.annotation.Nonnull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BaseResponse<T> {
    private T result;
    private ResponseException exception;

    @Nonnull
    public static BaseResponse<?> ofException(String message) {
        return BaseResponse.builder()
            .exception(new ResponseException(message))
            .build();
    }

    @Nonnull
    public static BaseResponse<?> ofResult(Object result) {
        return BaseResponse.builder()
            .result(result)
            .build();
    }
}

