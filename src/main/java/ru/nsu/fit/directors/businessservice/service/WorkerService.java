package ru.nsu.fit.directors.businessservice.service;

import ru.nsu.fit.directors.businessservice.dto.response.ResponseWorkerDto;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface WorkerService {

    /**
     * Найти работников заведения.
     *
     * @param establishmentId идентификатор заведения
     * @return список работников
     */
    @Nonnull
    List<ResponseWorkerDto> searchWorkers(Long establishmentId);

    /**
     * Получить всех работников.
     *
     * @return список всех работников
     */
    @Nonnull
    List<ResponseWorkerDto> getAllWorkers();
}
