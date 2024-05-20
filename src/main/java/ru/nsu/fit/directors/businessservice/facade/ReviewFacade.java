package ru.nsu.fit.directors.businessservice.facade;

import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.businessservice.dto.CreateAnswerRequest;

@ParametersAreNonnullByDefault
public interface ReviewFacade {
    /**
     * Создать ответ на отзыв.
     *
     * @param createAnswerRequest запрос ответа
     */
    void createAnswer(CreateAnswerRequest createAnswerRequest);
}
