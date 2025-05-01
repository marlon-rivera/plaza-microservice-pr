package com.pragma.plaza_service.domain.usecase;

import com.pragma.plaza_service.domain.api.IDishServicePort;
import com.pragma.plaza_service.domain.exception.InvalidDataException;
import com.pragma.plaza_service.domain.exception.ResourceConflictException;
import com.pragma.plaza_service.domain.exception.ResourceNotFoundException;
import com.pragma.plaza_service.domain.model.Dish;
import com.pragma.plaza_service.domain.model.DishCategory;
import com.pragma.plaza_service.domain.model.PaginationInfo;
import com.pragma.plaza_service.domain.model.Restaurant;
import com.pragma.plaza_service.domain.spi.IAutthenticatePort;
import com.pragma.plaza_service.domain.spi.IDishCategoryPersistencePort;
import com.pragma.plaza_service.domain.spi.IDishPersistencePort;
import com.pragma.plaza_service.domain.spi.IRestaurantPersistencePort;
import com.pragma.plaza_service.domain.util.constants.DishUseCaseConstants;
import com.pragma.plaza_service.domain.util.validators.DishValidator;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IDishCategoryPersistencePort dishCategoryPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IAutthenticatePort authenticatePort;

    @Override
    public void createDish(Dish dish, String dishCategoryName) {
        validateRestaurantOwner(dish.getRestaurantId());
        Optional<DishCategory> dishCategoryOptional = dishCategoryPersistencePort.findByName(dishCategoryName);
        if(dishCategoryOptional.isEmpty()) {
            throw new ResourceConflictException(DishUseCaseConstants.DISH_CATEGORY_NOT_FOUND);
        }
        dish.setCategory(dishCategoryOptional.get());
        DishValidator.validateDish(dish);
        dish.setActive(true);
        dishPersistencePort.createDish(dish);
    }

    @Override
    public void modifyDish(Long id, String description, BigDecimal price) {
        Optional<Dish> dishOptional = dishPersistencePort.findById(id);
        if(dishOptional.isEmpty()){
            throw new ResourceNotFoundException(DishUseCaseConstants.DISH_NOT_FOUND);
        }
        Dish dish = dishOptional.get();
        validateRestaurantOwner(dish.getRestaurantId());
        if (description != null && !description.isEmpty() && !description.equals(dish.getDescription())) {
            dish.setDescription(description);
        }
        if (price != null && price.compareTo(BigDecimal.ZERO) > 0 && !price.equals(dish.getPrice())) {
            dish.setPrice(price);
        }
        DishValidator.validateEditDish(dish);
        dishPersistencePort.modifyDish(dish);
    }

    @Override
    public void changeDishStatus(Long id, boolean status) {
        Optional<Dish> dishOptional = dishPersistencePort.findById(id);
        if(dishOptional.isEmpty()){
            throw new ResourceNotFoundException(DishUseCaseConstants.DISH_NOT_FOUND);
        }
        Dish dish = dishOptional.get();
        validateRestaurantOwner(dish.getRestaurantId());
        if (dish.isActive() == status) {
            return;
        }
        dish.setActive(status);
        dishPersistencePort.updateDish(dish);
    }

    @Override
    public PaginationInfo<Dish> listDishes(Long restaurantId, Long categoryId, int page, int sizePage) {
        Optional<DishCategory> dishCategoryOptional = dishCategoryPersistencePort.findById(categoryId);
        if (dishCategoryOptional.isEmpty()) {
            throw new ResourceNotFoundException(DishUseCaseConstants.DISH_CATEGORY_NOT_FOUND);
        }
        Optional<Restaurant> restaurantOptional = restaurantPersistencePort.findById(restaurantId);
        if (restaurantOptional.isEmpty()) {
            throw new ResourceNotFoundException(DishUseCaseConstants.RESTAURANT_NOT_FOUND);
        }
        return dishPersistencePort.listDishesByRestaurantAndCategory(restaurantId, categoryId, page, sizePage);
    }

    private void validateRestaurantOwner(Long restaurantId) {
        Long userId = authenticatePort.getCurrentUserId();
        Long restaurantOwnerId = restaurantPersistencePort.findOwnerIdByRestaurantId(restaurantId);
        if (!userId.equals(restaurantOwnerId)) {
            throw new InvalidDataException(DishUseCaseConstants.DIFFERENT_OWNER);
        }
    }
}
