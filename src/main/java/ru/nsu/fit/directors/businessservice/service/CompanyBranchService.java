package ru.nsu.fit.directors.businessservice.service;

import org.springframework.web.multipart.MultipartFile;
import ru.nsu.fit.directors.businessservice.dto.request.CompanyCreateRequest;
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
    void createCompanyBranch(CompanyCreateRequest companyCreateRequest);

    /**
     * Создать филиал заведения.
     *
     * @param companyCreateRequest запрос создания
     * @param images               фотографии заведения
     */
    void createCompanyBranch(CompanyCreateRequest companyCreateRequest, MultipartFile[] images);

    /**
     * Получить список заведений, принадлежащих создателю.
     *
     * @param name имя заведения
     * @return список заведений
     */
    @Nonnull
    List<ResponseShortEstablishmentInfo> getEstablishmentsByOwner(String name);
}
