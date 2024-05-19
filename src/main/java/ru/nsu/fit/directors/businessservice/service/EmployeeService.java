package ru.nsu.fit.directors.businessservice.service;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.model.Option;

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

    /**
     * Проверяет, вошел ли пользователь в аккаунт и имеет ли он права на осуществление данной опции в заведении.
     *
     * @param company заведение
     * @param option  необходимая опция
     */
    void validateWorker(Company company, Option option);

    /**
     * Получить активного пользователя.
     *
     * @return данные пользователя
     */
    @Nonnull
    BusinessUser getLoggedInUser();
}
