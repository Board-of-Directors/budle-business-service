package ru.nsu.fit.directors.businessservice.service;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.businessservice.model.BusinessUser;

@ParametersAreNonnullByDefault
public interface SecurityService {

    /**
     * Получить активного пользователя.
     *
     * @return пользователь
     */
    @Nonnull
    BusinessUser getLoggedInUser();

}
