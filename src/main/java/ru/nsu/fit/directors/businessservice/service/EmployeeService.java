package ru.nsu.fit.directors.businessservice.service;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface EmployeeService {
    /**
     * Проверяет, вошел ли пользователь в аккаунт и имеет ли он права на редактирования данного заведения.
     *
     * @param establishmentId идентификатор заведения.
     * @throws ru.nsu.fit.directors.businessservice.exceptions.NotEnoughRightException если недостаточно прав
     */
    default void validateWorker(Long establishmentId) {
        validateWorker(establishmentId, true);
    }

    /**
     * Проверяет, вошел ли пользователь в аккаунт и имеет ли он права на редактирования данного заведения.
     *
     * @param establishmentId идентификатор заведения
     * @param canBeWorker     может ли быть сотрудником заведения
     * @throws ru.nsu.fit.directors.businessservice.exceptions.NotEnoughRightException если недостаточно прав
     */
    void validateWorker(Long establishmentId, boolean canBeWorker);
}
