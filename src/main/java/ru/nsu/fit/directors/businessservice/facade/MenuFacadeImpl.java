package ru.nsu.fit.directors.businessservice.facade;

import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.api.EstablishmentServiceClient;
import ru.nsu.fit.directors.businessservice.dto.request.ChangeCategoryRequest;
import ru.nsu.fit.directors.businessservice.dto.request.ChangeProductRequest;
import ru.nsu.fit.directors.businessservice.dto.request.RequestCategoryDto;
import ru.nsu.fit.directors.businessservice.dto.request.RequestProductDto;
import ru.nsu.fit.directors.businessservice.model.Company;
import ru.nsu.fit.directors.businessservice.model.Option;
import ru.nsu.fit.directors.businessservice.service.CompanyService;
import ru.nsu.fit.directors.businessservice.service.EmployeeService;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class MenuFacadeImpl implements MenuFacade {
    private final EstablishmentServiceClient establishmentServiceClient;
    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @Override
    public void addCategory(RequestCategoryDto requestCategoryDto) {
        Company company = companyService.getById(requestCategoryDto.establishmentId());
        employeeService.validateWorker(company, Option.ADD_MENU_POSITION);
        establishmentServiceClient.add(requestCategoryDto);
    }

    @Override
    public void deleteCategory(Long establishmentId, Long categoryId) {
        Company company = companyService.getById(establishmentId);
        employeeService.validateWorker(company, Option.DELETE_MENU_POSITION);
        establishmentServiceClient.deleteCategory(categoryId);
    }

    @Override
    public void addProduct(RequestProductDto requestProductDto) {
        Company company = companyService.getById(requestProductDto.establishmentId());
        employeeService.validateWorker(company, Option.ADD_MENU_POSITION);
        establishmentServiceClient.addProduct(requestProductDto);
    }

    @Override
    public void deleteProduct(Long establishmentId, Long productId) {
        Company company = companyService.getById(establishmentId);
        employeeService.validateWorker(company, Option.DELETE_MENU_POSITION);
        establishmentServiceClient.deleteProduct(productId);
    }

    @Override
    public void changeCategory(ChangeCategoryRequest changeCategoryRequest) {
        establishmentServiceClient.changeCategory(changeCategoryRequest);
    }

    @Override
    public void changeProduct(ChangeProductRequest changeProductRequest) {
        establishmentServiceClient.changeProduct(changeProductRequest);
    }
}
