package com.pragma.plaza_service.domain.usecase;
import com.pragma.plaza_service.domain.exception.ResourceConflictException;
import com.pragma.plaza_service.domain.model.Dish;
import com.pragma.plaza_service.domain.model.DishCategory;
import com.pragma.plaza_service.domain.spi.IDishCategoryPersistencePort;
import com.pragma.plaza_service.domain.spi.IDishPersistencePort;
import com.pragma.plaza_service.domain.util.constants.DishUseCaseConstants;
import com.pragma.plaza_service.domain.util.validators.DishValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IDishCategoryPersistencePort dishCategoryPersistencePort;

    @InjectMocks
    private DishUseCase dishUseCase;

    private Dish dish;
    private DishCategory dishCategory;
    private final String categoryName = "Comida rápida";

    @BeforeEach
    void setUp() {
        dishCategory = new DishCategory();
        dishCategory.setId(1L);
        dishCategory.setName(categoryName);

        dish = new Dish();
        dish.setName("Hamburguesa");
        dish.setDescription("Hamburguesa clásica");
        dish.setPrice(BigDecimal.valueOf(15000));
        dish.setImageUrl("http://imagen.com/hamburguesa.jpg");
        dish.setRestaurantId(1L);
    }

    @Test
    void createDish_WhenCategoryExists_ThenCreatesDishSuccessfully() {
        // Arrange
        when(dishCategoryPersistencePort.findByName(categoryName)).thenReturn(Optional.of(dishCategory));

        try (MockedStatic<DishValidator> validator = Mockito.mockStatic(DishValidator.class)) {
            // Act
            dishUseCase.createDish(dish, categoryName);

            // Assert
            validator.verify(() -> DishValidator.validateDish(dish));
            verify(dishPersistencePort).createDish(dish);
            assertTrue(dish.isActive());
            assertEquals(dishCategory, dish.getCategory());
        }
    }

    @Test
    void createDish_WhenCategoryDoesNotExist_ThenThrowsException() {
        // Arrange
        when(dishCategoryPersistencePort.findByName(categoryName)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceConflictException exception = assertThrows(ResourceConflictException.class,
                () -> dishUseCase.createDish(dish, categoryName));

        assertEquals(DishUseCaseConstants.DISH_CATEGORY_NOT_FOUND, exception.getMessage());
        verify(dishPersistencePort, never()).createDish(any(Dish.class));
    }

    @Test
    void createDish_WhenCategoryExists_ThenSetsActiveToTrue() {
        // Arrange
        when(dishCategoryPersistencePort.findByName(categoryName)).thenReturn(Optional.of(dishCategory));

        try (MockedStatic<DishValidator> validator = Mockito.mockStatic(DishValidator.class)) {
            // Act
            dishUseCase.createDish(dish, categoryName);

            // Assert
            assertTrue(dish.isActive());
        }
    }

}