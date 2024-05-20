package ru.nsu.fit.directors.businessservice.facade;

import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.businessservice.api.EstablishmentServiceClient;
import ru.nsu.fit.directors.businessservice.dto.CreateAnswerRequest;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class ReviewFacadeImpl implements ReviewFacade {
    private final EstablishmentServiceClient establishmentServiceClient;

    @Override
    public void createAnswer(CreateAnswerRequest createAnswerRequest) {
        establishmentServiceClient.createAnswer(createAnswerRequest);
    }
}
