package ru.nsu.fit.directors.businessservice.configuration;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.exceptions.BaseException;
import ru.nsu.fit.directors.businessservice.exceptions.ClientException;
import ru.nsu.fit.directors.businessservice.exceptions.ServerNotAvailableException;

@Component
public class CustomErrorDecoderConfiguration implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400, 403, 404 -> new ClientException(response.reason());
            case 500, 503 -> new ServerNotAvailableException();
            default -> new BaseException("Cannot catch client exception", "CannotCatchException");
        };
    }
}

