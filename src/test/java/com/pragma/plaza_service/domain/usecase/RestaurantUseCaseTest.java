package com.pragma.plaza_service.domain.usecase;

import com.pragma.plaza_service.domain.exception.ResourceConflictException;
import com.pragma.plaza_service.domain.exception.UserNotOwnerException;
import com.pragma.plaza_service.domain.model.Restaurant;
import com.pragma.plaza_service.domain.spi.IRestaurantPersistencePort;
import com.pragma.plaza_service.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserPersistencePort userPersistencePort;

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

        assertThrows(UserNotOwnerException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });

        verify(restaurantPersistencePort, never()).saveRestaurant(any(Restaurant.class));
    }

    @Test
    void createRestaurant_WhenNitAlreadyExists_ShouldThrowResourceConflictException() {
        when(userPersistencePort.isOwner(restaurant.getOwnerId())).thenReturn(true);
        when(restaurantPersistencePort.existsByNit(restaurant.getNit())).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });

        verify(restaurantPersistencePort, never()).saveRestaurant(any(Restaurant.class));
    }

}