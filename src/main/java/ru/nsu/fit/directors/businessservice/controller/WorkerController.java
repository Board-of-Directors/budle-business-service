package ru.nsu.fit.directors.businessservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.businessservice.dto.request.InviteWorkerRequest;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseWorkerDto;
import ru.nsu.fit.directors.businessservice.facade.WorkerFacade;
import ru.nsu.fit.directors.businessservice.service.WorkerService;

import java.util.List;

@RestController
@RequestMapping("/business/worker")
@RequiredArgsConstructor
public class WorkerController {
    private final WorkerService workerService;
    private final WorkerFacade workerFacade;

    @GetMapping(value = "/all")
    public List<ResponseWorkerDto> getAllWorkers() {
        return workerService.getAllWorkers();
    }

    @GetMapping
    public List<ResponseWorkerDto> search(@RequestParam Long establishmentId) {
        return workerService.searchWorkers(establishmentId);
    }

    @PutMapping(value = "/invite")
    public void inviteWorker(@RequestBody @Valid InviteWorkerRequest inviteWorkerRequest) {
        workerFacade.inviteWorker(inviteWorkerRequest);
    }

    @PutMapping
    public void addWorker(@RequestBody @Valid AddWorkerRequest addWorkerRequest) {
        workerFacade.addWorker(addWorkerRequest);
    }

    @DeleteMapping
    public void delete(@RequestParam Long establishmentId, @RequestParam Long workerId) {
        workerService.deleteWorker(establishmentId, workerId);
    }
}
