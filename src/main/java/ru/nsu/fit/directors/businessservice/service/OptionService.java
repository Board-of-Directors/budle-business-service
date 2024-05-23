package ru.nsu.fit.directors.businessservice.service;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.businessservice.model.AvailableOption;
import ru.nsu.fit.directors.businessservice.model.BusinessUser;
import ru.nsu.fit.directors.businessservice.model.Company;

@ParametersAreNonnullByDefault
public interface OptionService {
    /**
     * Получить доступные опции работника.
     *
     * @param worker  работник
     * @param company компания
     * @return список доступных опций
     */
    @Nonnull
    List<AvailableOption> getByWorker(BusinessUser worker, Company company);

    /**
     * Заменить опции.
     *
     * @param actualOptions актуальные опции
     * @param company       компания
     * @param businessUser  пользователь
     */
    void replaceOptions(List<AvailableOption> actualOptions, BusinessUser businessUser, Company company);

    /**
     * Добавить начальные настройки работника.
     *
     * @param businessUser работник
     * @param company      компания
     * @param options      переданные настройки
     */
    void addInitialOptions(BusinessUser businessUser, Company company, List<AvailableOption> options);

    /**
     * Удалить все доступные опции у работника.
     * @param worker работник
     * @param company компания
     */
    void delete(BusinessUser worker, Company company);
}
