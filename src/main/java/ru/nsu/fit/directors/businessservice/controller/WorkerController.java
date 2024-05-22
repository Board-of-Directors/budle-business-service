package ru.nsu.fit.directors.businessservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseWorkerDto;
import ru.nsu.fit.directors.businessservice.service.WorkerService;

import java.util.List;

@RestController
@RequestMapping("/business/worker")
@RequiredArgsConstructor
public class WorkerController {
    private final WorkerService workerService;

    @GetMapping(value = "/all")
    public List<ResponseWorkerDto> getAllWorkers() {
        return workerService.getAllWorkers();
    }

    @GetMapping
    public List<ResponseWorkerDto> search(@RequestParam Long establishmentId) {
        return workerService.searchWorkers(establishmentId);
    }

    @PutMapping(value = "/invite")
    public void inviteWorker(@RequestParam Long establishmentId, @RequestParam String token){
        workerService.inviteWorker(establishmentId, token);
    }

    @PutMapping
    public void addWorker(@RequestParam Long workerId, @RequestParam Long establishmentId) {
        workerService.addWorker(workerId, establishmentId);
    }

    @DeleteMapping
    public void delete(@RequestParam Long establishmentId, @RequestParam Long workerId) {
        workerService.deleteWorker(establishmentId, workerId);
    }
}
