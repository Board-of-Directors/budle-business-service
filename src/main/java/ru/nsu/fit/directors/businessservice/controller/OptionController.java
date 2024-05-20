package ru.nsu.fit.directors.businessservice.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.businessservice.dto.response.AvailableOptionResponse;
import ru.nsu.fit.directors.businessservice.facade.OptionFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/business/option")
public class OptionController {
    private final OptionFacade optionFacade;

    @GetMapping
    public List<AvailableOptionResponse> getOptions(@RequestParam Long workerId, @RequestParam Long establishmentId) {
        return optionFacade.getAvailableOptions(workerId, establishmentId);
    }

    @GetMapping(value = "/all")
    public List<AvailableOptionResponse> getOptions(){
        return optionFacade.getAllOptions();
    }
}
