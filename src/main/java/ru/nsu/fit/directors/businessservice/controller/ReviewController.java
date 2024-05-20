package ru.nsu.fit.directors.businessservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.businessservice.dto.CreateAnswerRequest;
import ru.nsu.fit.directors.businessservice.facade.ReviewFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/business/review")
public class ReviewController {
    private final ReviewFacade reviewFacade;

    @PostMapping(value = "answer")
    public void answer(@RequestBody @Valid CreateAnswerRequest createAnswerRequest){
        reviewFacade.createAnswer(createAnswerRequest);
    }
}
