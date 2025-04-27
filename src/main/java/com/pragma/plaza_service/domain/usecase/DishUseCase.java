package com.pragma.plaza_service.domain.usecase;

import com.pragma.plaza_service.domain.api.IDishServicePort;
import com.pragma.plaza_service.domain.exception.ResourceConflictException;
import com.pragma.plaza_service.domain.model.Dish;
import com.pragma.plaza_service.domain.model.DishCategory;
import com.pragma.plaza_service.domain.spi.IDishCategoryPersistencePort;
import com.pragma.plaza_service.domain.spi.IDishPersistencePort;
import com.pragma.plaza_service.domain.util.constants.DishUseCaseConstants;
import com.pragma.plaza_service.domain.util.validators.DishValidator;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IDishCategoryPersistencePort dishCategoryPersistencePort;

    @Override
    public void createDish(Dish dish, String dishCategoryName) {
        Optional<DishCategory> dishCategoryOptional = dishCategoryPersistencePort.findByName(dishCategoryName);
        if(dishCategoryOptional.isEmpty()) {
            throw new ResourceConflictException(DishUseCaseConstants.DISH_CATEGORY_NOT_FOUND);
        }
        dish.setCategory(dishCategoryOptional.get());
        DishValidator.validateDish(dish);
        dish.setActive(true);
        dishPersistencePort.createDish(dish);
    }
}
