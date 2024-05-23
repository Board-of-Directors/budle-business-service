package ru.nsu.fit.directors.businessservice.facade;

import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.businessservice.controller.AddWorkerRequest;
import ru.nsu.fit.directors.businessservice.dto.request.InviteWorkerRequest;

@ParametersAreNonnullByDefault
public interface WorkerFacade {
    /**
     * Добавить работника в своих работников.
     *
     * @param inviteWorkerRequest запрос на добавление в работники
     */
    void inviteWorker(InviteWorkerRequest inviteWorkerRequest);

    /**
     * Добавить работника в заведение.
     *
     * @param addWorkerRequest запрос добавления работника
     */
    void addWorker(AddWorkerRequest addWorkerRequest);

    /**
     * Удаление работника из заведения.
     *
     * @param establishmentId идентификатор заведения
     * @param workerId        идентификатор работника
     */
    void deleteWorker(Long establishmentId, Long workerId);
}
