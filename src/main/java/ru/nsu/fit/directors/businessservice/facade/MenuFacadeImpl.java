package ru.nsu.fit.directors.businessservice.facade;

import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.api.EstablishmentServiceClient;
import ru.nsu.fit.directors.businessservice.dto.request.ChangeCategoryRequest;
import ru.nsu.fit.directors.businessservice.dto.request.ChangeProductRequest;
import ru.nsu.fit.directors.businessservice.dto.request.RequestCategoryDto;
import ru.nsu.fit.directors.businessservice.dto.request.RequestProductDto;
import ru.nsu.fit.directors.businessservice.service.EmployeeService;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class MenuFacadeImpl implements MenuFacade {
    private final EstablishmentServiceClient establishmentServiceClient;
    private final EmployeeService employeeService;

    @Override
    public void addCategory(RequestCategoryDto requestCategoryDto) {
        employeeService.validateOwner(requestCategoryDto.establishmentId());
        establishmentServiceClient.add(requestCategoryDto);
    }

    @Override
    public void deleteCategory(Long establishmentId, Long categoryId) {
        employeeService.validateOwner(establishmentId);
        establishmentServiceClient.deleteCategory(categoryId);
    }

    @Override
    public void addProduct(RequestProductDto requestProductDto) {
        employeeService.validateOwner(requestProductDto.establishmentId());
        establishmentServiceClient.addProduct(requestProductDto);
    }

    @Override
    public void deleteProduct(Long establishmentId, Long productId) {
        employeeService.validateOwner(establishmentId);
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
