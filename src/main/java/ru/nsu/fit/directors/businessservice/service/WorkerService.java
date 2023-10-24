package ru.nsu.fit.directors.businessservice.service;

import ru.nsu.fit.directors.businessservice.dto.request.RequestWorkerDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseWorkerDto;

import java.util.List;

public interface WorkerService {
    void createWorker(RequestWorkerDto workerDto);

    void deleteWorker(Long establishmentId, Long workerId);

    List<ResponseWorkerDto> searchWorkers(Long establishmentId);

    void addWorker(Long workerId, Long establishmentId);
}
