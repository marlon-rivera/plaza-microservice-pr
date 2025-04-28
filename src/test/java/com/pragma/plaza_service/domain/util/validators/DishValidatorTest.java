package com.pragma.plaza_service.domain.util.validators;

import com.pragma.plaza_service.domain.exception.InvalidDataException;
import com.pragma.plaza_service.domain.model.Dish;
import com.pragma.plaza_service.domain.model.DishCategory;
import com.pragma.plaza_service.domain.util.constants.DishValidatorConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DishValidatorTest {

    @Test
    @DisplayName("Must not throw exception when the dish is valid")
    void validateDish_withValidDish_shouldNotThrowException() {
        // Arrange
        Dish dish = Dish.builder()
                .name("Pizza")
                .description("Deliciosa pizza")
                .price(new BigDecimal("15.99"))
                .imageUrl("http://example.com/image.jpg")
                .category(
                        new DishCategory(1L, "Pizza", "Deliciosa pizza")
                )
                .restaurantId(1L)
                .build();

        // Act & Assert
        assertDoesNotThrow(() -> DishValidator.validateDish(dish));
    }

    @Test
    @DisplayName("Must throw exception when the name is null")
    void validateDish_withNullName_shouldThrowException() {
        // Arrange
        Dish dish = Dish.builder()
                .name(null)
                .description("Descripción")
                .price(new BigDecimal("10.0"))
                .imageUrl("http://image.url")
                .category(new DishCategory(
                        1L, "Pizza", "Deliciosa pizza"
                ))
                .restaurantId(1L)
                .build();

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> DishValidator.validateDish(dish));
        assertEquals(DishValidatorConstants.NAME_REQUIRED, exception.getMessage());
    }

    @Test
    @DisplayName("Must throw exception when the name is empty")
    void validateDish_withEmptyName_shouldThrowException() {
        // Arrange
        Dish dish = Dish.builder()
                .name("  ")
                .description("Descripción")
                .price(new BigDecimal("10.0"))
                .imageUrl("http://image.url")
                .category(new DishCategory(
                        1L, "Pizza", "Deliciosa pizza"
                        )
                )
                .restaurantId(1L)
                .build();

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> DishValidator.validateDish(dish));
        assertEquals(DishValidatorConstants.NAME_REQUIRED, exception.getMessage());
    }

    @Test
    @DisplayName("Must throw exception when the description is empty")
    void validateDish_withEmptyDescription_shouldThrowException() {
        // Arrange
        Dish dish = Dish.builder()
                .name("Pizza")
                .description("")
                .price(new BigDecimal("10.0"))
                .imageUrl("http://image.url")
                .category(
                        new DishCategory(
                                1L, "Pizza", "Deliciosa pizza"
                        )
                )
                .restaurantId(1L)
                .build();

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> DishValidator.validateDish(dish));
        assertEquals(DishValidatorConstants.DESCRIPTION_REQUIRED, exception.getMessage());
    }

    @Test
    @DisplayName("Must throw exception when the description is null")
    void validateDish_withNullDescription_shouldThrowException() {
        // Arrange
        Dish dish = Dish.builder()
                .name("Pizza")
                .description(null)
                .price(new BigDecimal("10.0"))
                .imageUrl("http://image.url")
                .category(
                        new DishCategory(
                                1L, "Pizza", "Deliciosa pizza"
                        )
                )
                .restaurantId(1L)
                .build();

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> DishValidator.validateDish(dish));
        assertEquals(DishValidatorConstants.DESCRIPTION_REQUIRED, exception.getMessage());
    }

    @Test
    @DisplayName("Must throw exception when the description is empty")
    void validateDish_withNullImageUrl_shouldThrowException() {
        // Arrange
        Dish dish = Dish.builder()
                .name("Pizza")
                .description("Deliciosa pizza")
                .price(new BigDecimal("10.0"))
                .imageUrl(null)
                .category(
                        new DishCategory(
                                1L, "Pizza", "Deliciosa pizza"
                        )
                )
                .restaurantId(1L)
                .build();

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> DishValidator.validateDish(dish));
        assertEquals(DishValidatorConstants.IMAGE_URL_REQUIRED, exception.getMessage());
    }

    @Test
    @DisplayName("Must throw exception when the imageUrl is empty")
    void validateDish_withNullCategory_shouldThrowException() {
        // Arrange
        Dish dish = Dish.builder()
                .name("Pizza")
                .description("Deliciosa pizza")
                .price(new BigDecimal("10.0"))
                .imageUrl("http://image.url")
                .category(null)
                .restaurantId(1L)
                .build();

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> DishValidator.validateDish(dish));
        assertEquals(DishValidatorConstants.CATEGORY_REQUIRED, exception.getMessage());
    }

    @Test
    @DisplayName("Must throw exception when the imageUrl is empty")
    void validateDish_withEmptyImageUrl_shouldThrowException() {
        // Arrange
        Dish dish = Dish.builder()
                .name("Pizza")
                .description("Deliciosa pizza")
                .price(new BigDecimal("10.0"))
                .imageUrl("")
                .category(
                        new DishCategory(
                                1L, "Pizza", "Deliciosa pizza"
                        )
                )
                .restaurantId(1L)
                .build();

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> DishValidator.validateDish(dish));
        assertEquals(DishValidatorConstants.IMAGE_URL_REQUIRED, exception.getMessage());
    }

    @Test
    @DisplayName("Must throw exception when the price is null")
    void validateDish_withNullPrice_shouldThrowException() {
        // Arrange
        Dish dish = Dish.builder()
                .name("Pizza")
                .description("Deliciosa pizza")
                .price(null)
                .imageUrl("http://image.url")
                .category(
                        new DishCategory(
                                1L, "Pizza", "Deliciosa pizza"
                        )
                )
                .restaurantId(1L)
                .build();

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> DishValidator.validateDish(dish));
        assertEquals(DishValidatorConstants.INVALID_PRICE, exception.getMessage());
    }

    @Test
    @DisplayName("Must throw exception when the price is zero")
    void validateDish_withZeroPrice_shouldThrowException() {
        // Arrange
        Dish dish = Dish.builder()
                .name("Pizza")
                .description("Deliciosa pizza")
                .price(BigDecimal.ZERO)
                .imageUrl("http://image.url")
                .category(
                        new DishCategory(
                                1L, "Pizza", "Deliciosa pizza"
                        )
                )
                .restaurantId(1L)
                .build();

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> DishValidator.validateDish(dish));
        assertEquals(DishValidatorConstants.INVALID_PRICE, exception.getMessage());
    }

    @Test
    @DisplayName("Must throw exception when the price is negative")
    void validateDish_withNegativePrice_shouldThrowException() {
        // Arrange
        Dish dish = Dish.builder()
                .name("Pizza")
                .description("Deliciosa pizza")
                .price(new BigDecimal("-10.0"))
                .imageUrl("http://image.url")
                .category(
                        new DishCategory(
                                1L, "Pizza", "Deliciosa pizza"
                        )
                )
                .restaurantId(1L)
                .build();

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> DishValidator.validateDish(dish));
        assertEquals(DishValidatorConstants.INVALID_PRICE, exception.getMessage());
    }

    @Test
    @DisplayName("Must throw exception when restaurantId is null")
    void validateDish_withNullRestaurantId_shouldThrowException() {
        // Arrange
        Dish dish = Dish.builder()
                .name("Pizza")
                .description("Deliciosa pizza")
                .price(new BigDecimal("10.0"))
                .imageUrl("http://image.url")
                .category(
                        new DishCategory(1L, "Pizza", "Deliciosa pizza")
                )
                .restaurantId(null)
                .build();

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> DishValidator.validateDish(dish));
        assertEquals(DishValidatorConstants.RESTAURANT_REQUIRED, exception.getMessage());
    }

    @Test
    @DisplayName("Must not throw exception when the dish is valid for edit")
    void validateEditDish_withValidDish_shouldNotThrowException() {
        // Arrange
        Dish dish = Dish.builder()
                .name("Pizza Editada")
                .description("Deliciosa pizza editada")
                .price(new BigDecimal("17.99"))
                .imageUrl("http://example.com/new-image.jpg")
                .category(
                        new DishCategory(1L, "Pizza", "Deliciosa pizza")
                )
                .build();

        // Act & Assert
        assertDoesNotThrow(() -> DishValidator.validateEditDish(dish));
    }

}