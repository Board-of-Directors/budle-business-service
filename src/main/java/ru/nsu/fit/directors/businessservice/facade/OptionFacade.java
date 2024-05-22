package ru.nsu.fit.directors.businessservice.facade;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.businessservice.dto.ChangeOptionRequest;
import ru.nsu.fit.directors.businessservice.dto.response.AvailableOptionResponse;

@ParametersAreNonnullByDefault
public interface OptionFacade {
    /**
     * Получить доступные возможности работника.
     *
     * @param workerId        идентификатор работника
     * @param establishmentId идентификатор заведения
     * @return список доступных опций
     */
    @Nonnull
    List<AvailableOptionResponse> getAvailableOptions(Long workerId, Long establishmentId);

    /**
     * Получить все доступные опции.
     *
     * @return список опций
     */
    @Nonnull
    List<AvailableOptionResponse> getAllOptions();

    /**
     * Поменять настройки.
     *
     * @param changeOptionRequest запрос изменения опций
     */
    void changeOptions(ChangeOptionRequest changeOptionRequest);
}
