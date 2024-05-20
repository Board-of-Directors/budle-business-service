package ru.nsu.fit.directors.businessservice.service;

import ru.nsu.fit.directors.businessservice.dto.response.ResponseWorkerDto;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface WorkerService {

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

    /**
     * Получить всех работников.
     *
     * @return список всех работников
     */
    @Nonnull
    List<ResponseWorkerDto> getAllWorkers();

    /**
     * Пригласить работника в заведение.
     *
     * @param establishmentId идентификатор заведения
     * @param token           токен работника
     */
    void inviteWorker(Long establishmentId, String token);
}
