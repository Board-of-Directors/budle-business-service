package ru.nsu.fit.directors.businessservice.service;

import ru.nsu.fit.directors.businessservice.dto.request.CompanyCreateRequestV2;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseShortEstablishmentInfo;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;

@ParametersAreNonnullByDefault
public interface CompanyBranchService {

    /**
     * Создать филиал заведения.
     *
     * @param companyCreateRequest запрос создания
     */
    void createCompanyBranch(CompanyCreateRequestV2 companyCreateRequest);

    /**
     * Получить список заведений, принадлежащих создателю.
     *
     * @param name имя заведения
     * @return список заведений
     */
    @Nonnull
    List<ResponseShortEstablishmentInfo> getEstablishmentsByOwner(String name);
}
