package com.pragma.plaza_service.domain.usecase;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IAutthenticatePort authenticatePort;

    @Mock
    private IDishCategoryPersistencePort dishCategoryPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

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
        when(authenticatePort.getCurrentUserId()).thenReturn(1L);
        when(restaurantPersistencePort.findOwnerIdByRestaurantId(dish.getRestaurantId())).thenReturn(1L);
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
        when(authenticatePort.getCurrentUserId()).thenReturn(1L);
        when(restaurantPersistencePort.findOwnerIdByRestaurantId(dish.getRestaurantId())).thenReturn(1L);
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
        when(authenticatePort.getCurrentUserId()).thenReturn(1L);
        when(restaurantPersistencePort.findOwnerIdByRestaurantId(dish.getRestaurantId())).thenReturn(1L);
        try (MockedStatic<DishValidator> validator = Mockito.mockStatic(DishValidator.class)) {
            // Act
            dishUseCase.createDish(dish, categoryName);

            // Assert
            assertTrue(dish.isActive());
        }
    }

    @Test
    void modifyDish_WhenDishExists_ThenUpdatesDishSuccessfully() {
        // Arrange
        Long dishId = 1L;
        String newDescription = "Nueva descripción";
        BigDecimal newPrice = BigDecimal.valueOf(20000);

        when(dishPersistencePort.findById(dishId)).thenReturn(Optional.of(dish));
        when(authenticatePort.getCurrentUserId()).thenReturn(1L);
        when(restaurantPersistencePort.findOwnerIdByRestaurantId(dish.getRestaurantId())).thenReturn(1L);
        try (MockedStatic<DishValidator> validator = Mockito.mockStatic(DishValidator.class)) {
            // Act
            dishUseCase.modifyDish(dishId, newDescription, newPrice);

            // Assert
            validator.verify(() -> DishValidator.validateEditDish(dish));
            verify(dishPersistencePort).modifyDish(dish);
            assertEquals(newDescription, dish.getDescription());
            assertEquals(newPrice, dish.getPrice());
        }
    }

    @Test
    void modifyDish_WhenDescriptionIsNullOrEmpty_ThenKeepsOriginalDescription() {
        // Arrange
        Long dishId = 1L;
        String originalDescription = "Hamburguesa clásica";
        BigDecimal newPrice = BigDecimal.valueOf(20000);

        when(dishPersistencePort.findById(dishId)).thenReturn(Optional.of(dish));
        when(authenticatePort.getCurrentUserId()).thenReturn(1L);
        when(restaurantPersistencePort.findOwnerIdByRestaurantId(dish.getRestaurantId())).thenReturn(1L);
        try (MockedStatic<DishValidator> validator = Mockito.mockStatic(DishValidator.class)) {
            // Act con descripción null
            dishUseCase.modifyDish(dishId, null, newPrice);

            // Assert
            assertEquals(originalDescription, dish.getDescription());

            // Act con descripción vacía
            dishUseCase.modifyDish(dishId, "", newPrice);

            // Assert
            assertEquals(originalDescription, dish.getDescription());
        }
    }

    @Test
    void modifyDish_WhenPriceIsNullOrZeroOrNegative_ThenKeepsOriginalPrice() {
        // Arrange
        Long dishId = 1L;
        String newDescription = "Hamburguesa clásica";
        BigDecimal originalPrice = BigDecimal.valueOf(15000);

        when(dishPersistencePort.findById(dishId)).thenReturn(Optional.of(dish));
        when(authenticatePort.getCurrentUserId()).thenReturn(1L);
        when(restaurantPersistencePort.findOwnerIdByRestaurantId(dish.getRestaurantId())).thenReturn(1L);
        try (MockedStatic<DishValidator> validator = Mockito.mockStatic(DishValidator.class)) {
            // Act con precio null
            dishUseCase.modifyDish(dishId, newDescription, null);

            // Assert
            assertEquals(originalPrice, dish.getPrice());

            // Act con precio cero
            dishUseCase.modifyDish(dishId, newDescription, BigDecimal.ZERO);

            // Assert
            assertEquals(originalPrice, dish.getPrice());

            // Act con precio negativo
            dishUseCase.modifyDish(dishId, newDescription, BigDecimal.valueOf(-100));

            // Assert
            assertEquals(originalPrice, dish.getPrice());
        }
    }

    @Test
    void modifyDish_WhenDishNotFound_ThenThrowsException() {
        // Arrange
        Long dishId = 1L;
        when(dishPersistencePort.findById(dishId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> dishUseCase.modifyDish(dishId, "Nueva descripción", BigDecimal.valueOf(20000)));

        assertEquals(DishUseCaseConstants.DISH_NOT_FOUND, exception.getMessage());
        verify(dishPersistencePort, never()).modifyDish(any(Dish.class));
    }

    @Test
    void modifyDish_WhenUserIsNotRestaurantOwner_ThenThrowsException() {
        // Arrange
        Long dishId = 1L;
        when(dishPersistencePort.findById(dishId)).thenReturn(Optional.of(dish));
        when(authenticatePort.getCurrentUserId()).thenReturn(2L);
        when(restaurantPersistencePort.findOwnerIdByRestaurantId(dish.getRestaurantId())).thenReturn(1L);

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> dishUseCase.modifyDish(dishId, "Nueva descripción", BigDecimal.valueOf(20000)));

        assertEquals(DishUseCaseConstants.DIFFERENT_OWNER, exception.getMessage());
        verify(dishPersistencePort, never()).modifyDish(any(Dish.class));
    }

    @Test
    void createDish_WhenUserIsNotRestaurantOwner_ThenThrowsException() {
        // Arrange
        when(authenticatePort.getCurrentUserId()).thenReturn(2L);
        when(restaurantPersistencePort.findOwnerIdByRestaurantId(dish.getRestaurantId())).thenReturn(1L);

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> dishUseCase.createDish(dish, categoryName));

        assertEquals(DishUseCaseConstants.DIFFERENT_OWNER, exception.getMessage());
        verify(dishPersistencePort, never()).createDish(any(Dish.class));
    }

    @Test
    void changeDishStatus_WhenDishExists_ThenChangesStatusSuccessfully() {
        // Arrange
        Long dishId = 1L;
        dish.setActive(true);
        boolean newStatus = false;

        when(dishPersistencePort.findById(dishId)).thenReturn(Optional.of(dish));
        when(authenticatePort.getCurrentUserId()).thenReturn(1L);
        when(restaurantPersistencePort.findOwnerIdByRestaurantId(dish.getRestaurantId())).thenReturn(1L);

        // Act
        dishUseCase.changeDishStatus(dishId, newStatus);

        // Assert
        assertFalse(dish.isActive());
        verify(dishPersistencePort).updateDish(dish);
    }

    @Test
    void changeDishStatus_WhenDishNotFound_ThenThrowsException() {
        // Arrange
        Long dishId = 1L;
        when(dishPersistencePort.findById(dishId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> dishUseCase.changeDishStatus(dishId, false));

        assertEquals(DishUseCaseConstants.DISH_NOT_FOUND, exception.getMessage());
        verify(dishPersistencePort, never()).updateDish(any(Dish.class));
    }

    @Test
    void changeDishStatus_WhenUserIsNotRestaurantOwner_ThenThrowsException() {
        // Arrange
        Long dishId = 1L;

        when(dishPersistencePort.findById(dishId)).thenReturn(Optional.of(dish));
        when(authenticatePort.getCurrentUserId()).thenReturn(2L);
        when(restaurantPersistencePort.findOwnerIdByRestaurantId(dish.getRestaurantId())).thenReturn(1L);

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> dishUseCase.changeDishStatus(dishId, false));

        assertEquals(DishUseCaseConstants.DIFFERENT_OWNER, exception.getMessage());
        verify(dishPersistencePort, never()).updateDish(any(Dish.class));
    }

    @Test
    void changeDishStatus_WhenStatusIsAlreadyTheSame_ThenDoesNothing() {
        // Arrange
        Long dishId = 1L;
        dish.setActive(true);
        boolean sameStatus = true;

        when(dishPersistencePort.findById(dishId)).thenReturn(Optional.of(dish));
        when(authenticatePort.getCurrentUserId()).thenReturn(1L);
        when(restaurantPersistencePort.findOwnerIdByRestaurantId(dish.getRestaurantId())).thenReturn(1L);

        // Act
        dishUseCase.changeDishStatus(dishId, sameStatus);

        // Assert
        assertTrue(dish.isActive());
        verify(dishPersistencePort, never()).updateDish(any(Dish.class));
    }

    @Test
    void listDishes_WhenRestaurantAndCategoryExist_ThenReturnsDishesList() {
        // Arrange
        Long restaurantId = 1L;
        Long categoryId = 1L;
        int page = 0;
        int sizePage = 10;
        PaginationInfo<Dish> expectedPaginationInfo = new PaginationInfo<>(
                List.of(dish),
                page,
                sizePage,
                1L,
                1,
                false,
                false
        );

        when(dishCategoryPersistencePort.findById(categoryId)).thenReturn(Optional.of(dishCategory));
        when(restaurantPersistencePort.findById(restaurantId)).thenReturn(Optional.of(mock(Restaurant.class)));
        when(dishPersistencePort.listDishesByRestaurantAndCategory(restaurantId, categoryId, page, sizePage))
                .thenReturn(expectedPaginationInfo);

        // Act
        PaginationInfo<Dish> result = dishUseCase.listDishes(restaurantId, categoryId, page, sizePage);

        // Assert
        assertNotNull(result);
        assertEquals(expectedPaginationInfo, result);
        verify(dishCategoryPersistencePort).findById(categoryId);
        verify(restaurantPersistencePort).findById(restaurantId);
        verify(dishPersistencePort).listDishesByRestaurantAndCategory(restaurantId, categoryId, page, sizePage);
    }

    @Test
    void listDishes_WhenCategoryDoesNotExist_ThenThrowsResourceNotFoundException() {
        // Arrange
        Long restaurantId = 1L;
        Long categoryId = 1L;
        int page = 0;
        int sizePage = 10;

        when(dishCategoryPersistencePort.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> dishUseCase.listDishes(restaurantId, categoryId, page, sizePage));

        assertEquals(DishUseCaseConstants.DISH_CATEGORY_NOT_FOUND, exception.getMessage());
        verify(dishCategoryPersistencePort).findById(categoryId);
        verify(restaurantPersistencePort, never()).findById(any());
        verify(dishPersistencePort, never()).listDishesByRestaurantAndCategory(any(), any(), anyInt(), anyInt());
    }

    @Test
    void listDishes_WhenRestaurantDoesNotExist_ThenThrowsResourceNotFoundException() {
        // Arrange
        Long restaurantId = 1L;
        Long categoryId = 1L;
        int page = 0;
        int sizePage = 10;

        when(dishCategoryPersistencePort.findById(categoryId)).thenReturn(Optional.of(dishCategory));
        when(restaurantPersistencePort.findById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> dishUseCase.listDishes(restaurantId, categoryId, page, sizePage));

        assertEquals(DishUseCaseConstants.RESTAURANT_NOT_FOUND, exception.getMessage());
        verify(dishCategoryPersistencePort).findById(categoryId);
        verify(restaurantPersistencePort).findById(restaurantId);
        verify(dishPersistencePort, never()).listDishesByRestaurantAndCategory(any(), any(), anyInt(), anyInt());
    }

}