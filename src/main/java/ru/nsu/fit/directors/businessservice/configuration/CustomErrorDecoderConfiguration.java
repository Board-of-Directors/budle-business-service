package ru.nsu.fit.directors.businessservice.configuration;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.dto.response.BaseResponse;
import ru.nsu.fit.directors.businessservice.exceptions.BaseException;
import ru.nsu.fit.directors.businessservice.exceptions.ClientException;
import ru.nsu.fit.directors.businessservice.exceptions.ServerNotAvailableException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomErrorDecoderConfiguration implements ErrorDecoder {
    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400, 403, 404 -> {
                try {
                    BaseResponse<?> baseResponse = objectMapper.readValue(
                        response.body().asInputStream(),
                        BaseResponse.class
                    );
                    yield new ClientException(baseResponse.getException().getMessage());
                } catch (IOException e) {
                    log.error("Uncaught client exception {}", e.getMessage());
                    yield new ClientException();
                }
            }
            case 500, 503 -> new ServerNotAvailableException();
            default -> {
                log.error("Uncaught client status {}", response.status());
                yield new BaseException("Cannot catch client exception", "CannotCatchException");
            }
        };
    }
}

