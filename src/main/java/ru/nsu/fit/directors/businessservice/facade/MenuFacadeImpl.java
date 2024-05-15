package ru.nsu.fit.directors.businessservice.facade;

import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.businessservice.api.EstablishmentServiceClient;
import ru.nsu.fit.directors.businessservice.dto.request.RequestCategoryDto;
import ru.nsu.fit.directors.businessservice.dto.request.RequestProductDto;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class MenuFacadeImpl implements MenuFacade {
    private final EstablishmentServiceClient establishmentServiceClient;

    @Override
    public void addCategory(RequestCategoryDto requestCategoryDto) {
        establishmentServiceClient.add(requestCategoryDto);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        establishmentServiceClient.deleteCategory(categoryId);
    }

    @Override
    public void addProduct(RequestProductDto requestProductDto) {
        establishmentServiceClient.addProduct(requestProductDto);
    }

    @Override
    public void deleteProduct(Long productId) {
        establishmentServiceClient.deleteProduct(productId);
    }
}
