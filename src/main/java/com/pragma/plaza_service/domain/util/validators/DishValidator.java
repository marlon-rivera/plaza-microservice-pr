package com.pragma.plaza_service.domain.util.validators;

import com.pragma.plaza_service.domain.exception.InvalidDataException;
import com.pragma.plaza_service.domain.model.Dish;
import com.pragma.plaza_service.domain.util.constants.DishValidatorConstants;

import java.math.BigDecimal;

public class DishValidator {

    private DishValidator(){}

    public static void validateDish(Dish dish){
        validateRequiredFields(dish);
        validatePrice(dish.getPrice());
        validateRestaurantAssociation(dish);
    }

    private static void validateRequiredFields(Dish dish) {
        if (dish.getName() == null || dish.getName().trim().isEmpty()) {
            throw new InvalidDataException(DishValidatorConstants.NAME_REQUIRED);
        }

        if (dish.getDescription() == null || dish.getDescription().trim().isEmpty()) {
            throw new InvalidDataException(DishValidatorConstants.DESCRIPTION_REQUIRED);
        }

        if (dish.getImageUrl() == null || dish.getImageUrl().trim().isEmpty()) {
            throw new InvalidDataException(DishValidatorConstants.IMAGE_URL_REQUIRED);
        }

        if (dish.getCategory() == null) {
            throw new InvalidDataException(DishValidatorConstants.CATEGORY_REQUIRED);
        }
    }

    private static void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDataException(DishValidatorConstants.INVALID_PRICE);
        }
    }

    private static void validateRestaurantAssociation(Dish dish) {
        if (dish.getRestaurantId() == null) {
            throw new InvalidDataException(DishValidatorConstants.RESTAURANT_REQUIRED);
        }
    }

}
