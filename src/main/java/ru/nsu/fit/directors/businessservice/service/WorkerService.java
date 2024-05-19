package ru.nsu.fit.directors.businessservice.service;

import ru.nsu.fit.directors.businessservice.dto.request.RequestWorkerDto;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseWorkerDto;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface WorkerService {
    /**
     * Создать работника заведения.
     *
     * @param workerDto данные о работнике
     */
    void createWorker(RequestWorkerDto workerDto);

    /**
     * Удалить работника заведения.
     *
     * @param establishmentId идентификатор заведения
     * @param workerId        идентификатор работника
     */
    void deleteWorker(Long establishmentId, Long workerId);

    /**
     * Найти работников заведения.
     *
     * @param establishmentId идентификатор заведения
     * @return список работников
     */
    @Nonnull
    List<ResponseWorkerDto> searchWorkers(Long establishmentId);

    /**
     * Добавить работника в заведение.
     *
     * @param workerId        идентификатор работника
     * @param establishmentId идентификатор заведения
     */
    void addWorker(Long workerId, Long establishmentId);
}
