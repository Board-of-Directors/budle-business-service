package ru.nsu.fit.directors.businessservice.service;


import ru.nsu.fit.directors.businessservice.dto.request.CompanyCreateRequest;
import ru.nsu.fit.directors.businessservice.dto.response.ResponseShortEstablishmentInfo;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public interface CompanyBranchService {
    void createCompanyBranch(CompanyCreateRequest companyCreateRequest);

    List<ResponseShortEstablishmentInfo> getEstablishmentsByOwner(String name);
}
