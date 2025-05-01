package com.pragma.plaza_service.domain.usecase;

import com.pragma.plaza_service.domain.exception.ResourceConflictException;
import com.pragma.plaza_service.domain.exception.UserNotOwnerException;
import com.pragma.plaza_service.domain.model.PaginationInfo;
import com.pragma.plaza_service.domain.model.Restaurant;
import com.pragma.plaza_service.domain.spi.IAutthenticatePort;
import com.pragma.plaza_service.domain.spi.IRestaurantPersistencePort;
import com.pragma.plaza_service.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IAutthenticatePort autthenticatePort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setName("Restaurante Test");
        restaurant.setNit("123456789");
        restaurant.setAddress("Calle Test #123");
        restaurant.setPhoneNumber("+573507310045");
        restaurant.setLogoUrl("https://test-logo.com/logo.png");
        restaurant.setOwnerId(1L);
    }

    @Test
    void createRestaurant_WhenOwnerIsValidAndNitDoesNotExist_ShouldSaveRestaurant() {
        when(userPersistencePort.isOwner(restaurant.getOwnerId())).thenReturn(true);
        when(restaurantPersistencePort.existsByNit(restaurant.getNit())).thenReturn(false);

        restaurantUseCase.createRestaurant(restaurant);

        verify(restaurantPersistencePort).saveRestaurant(restaurant);
    }

    @Test
    void createRestaurant_WhenUserIsNotOwner_ShouldThrowUserNotOwnerException() {
        when(userPersistencePort.isOwner(restaurant.getOwnerId())).thenReturn(false);

        assertThrows(UserNotOwnerException.class, () -> restaurantUseCase.createRestaurant(restaurant));

        verify(restaurantPersistencePort, never()).saveRestaurant(any(Restaurant.class));
    }

    @Test
    void createRestaurant_WhenNitAlreadyExists_ShouldThrowResourceConflictException() {
        when(userPersistencePort.isOwner(restaurant.getOwnerId())).thenReturn(true);
        when(restaurantPersistencePort.existsByNit(restaurant.getNit())).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> restaurantUseCase.createRestaurant(restaurant));

        verify(restaurantPersistencePort, never()).saveRestaurant(any(Restaurant.class));
    }

    @Test
    void validateOwnerRestaurant_WhenCurrentUserIsOwner_ShouldReturnTrue() {
        Long restaurantId = 1L;
        Long ownerId = 10L;

        when(autthenticatePort.getCurrentUserId()).thenReturn(ownerId);
        when(restaurantPersistencePort.findOwnerIdByRestaurantId(restaurantId)).thenReturn(ownerId);

        boolean result = restaurantUseCase.validateOwnerRestaurant(restaurantId);

        assertTrue(result);
    }

    @Test
    void validateOwnerRestaurant_WhenCurrentUserIsNotOwner_ShouldReturnFalse() {
        Long restaurantId = 1L;
        Long ownerId = 10L;
        Long currentUserId = 20L;

        when(autthenticatePort.getCurrentUserId()).thenReturn(currentUserId);
        when(restaurantPersistencePort.findOwnerIdByRestaurantId(restaurantId)).thenReturn(ownerId);

        boolean result = restaurantUseCase.validateOwnerRestaurant(restaurantId);

        assertFalse(result);
    }

    @Test
    void listRestaurants_ShouldReturnPaginatedRestaurants() {
        // Arrange
        int page = 0;
        int sizePage = 10;

        List<Restaurant> restaurants = List.of(restaurant);
        PaginationInfo<Restaurant> expectedPagination = new PaginationInfo<>(
            restaurants,
            page,
            sizePage,
            1L,
            1,
            false,
            false

        );

        when(restaurantPersistencePort.listRestaurants(page, sizePage)).thenReturn(expectedPagination);

        // Act
        PaginationInfo<Restaurant> result = restaurantUseCase.listRestaurants(page, sizePage);

        // Assert
        assertEquals(expectedPagination, result);
        verify(restaurantPersistencePort).listRestaurants(page, sizePage);
    }

    @Test
    void listRestaurants_WithEmptyResult_ShouldReturnEmptyPagination() {
        // Arrange
        int page = 0;
        int sizePage = 10;

        PaginationInfo<Restaurant> emptyPagination = new PaginationInfo<>(
            List.of(),
            page,
            sizePage,
            0L,
            0,
            false,
            false
        );

        when(restaurantPersistencePort.listRestaurants(page, sizePage)).thenReturn(emptyPagination);

        // Act
        PaginationInfo<Restaurant> result = restaurantUseCase.listRestaurants(page, sizePage);

        // Assert
        assertEquals(emptyPagination, result);
        assertEquals(0, result.getList().size());
        verify(restaurantPersistencePort).listRestaurants(page, sizePage);
    }

}