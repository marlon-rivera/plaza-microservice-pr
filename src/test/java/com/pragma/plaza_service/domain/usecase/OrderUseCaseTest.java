package com.pragma.plaza_service.domain.usecase;

import com.pragma.plaza_service.domain.exception.InvalidDataException;
import com.pragma.plaza_service.domain.exception.ResourceConflictException;
import com.pragma.plaza_service.domain.model.*;
import com.pragma.plaza_service.domain.spi.IAutthenticatePort;
import com.pragma.plaza_service.domain.spi.IDishPersistencePort;
import com.pragma.plaza_service.domain.spi.IOrderPersistencePort;
import com.pragma.plaza_service.domain.spi.IRestaurantPersistencePort;
import com.pragma.plaza_service.domain.util.constants.OrderUseCaseConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IAutthenticatePort autthenticatePort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    private Order order;
    private Restaurant restaurant;
    private Dish dish;
    private OrderDish orderDish;
    private final Long clientId = 1L;
    private final Long restaurantId = 2L;
    private final Long dishId = 3L;

    @BeforeEach
    void setUp() {
        // Configurar objetos de prueba
        restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        dish = new Dish();
        dish.setId(dishId);
        dish.setRestaurantId(restaurantId);

        orderDish = new OrderDish();
        orderDish.setDish(dish);
        orderDish.setQuantity(2);

        order = new Order();
        order.setRestaurant(restaurant);
        order.setOrderDishes(List.of(orderDish));
    }

    @Test
    void createOrder_Successfully() {
        // Arrange
        when(autthenticatePort.getCurrentUserId()).thenReturn(clientId);
        when(restaurantPersistencePort.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(dishPersistencePort.findById(dishId)).thenReturn(Optional.of(dish));
        when(orderPersistencePort.existsOrderInProgressByClientId(clientId)).thenReturn(false);

        // Act
        orderUseCase.createOrder(order);

        // Assert
        verify(orderPersistencePort).saveOrder(any(Order.class));
        assertEquals(clientId, order.getClientId());
        assertEquals(StatusOrderEnum.PENDING, order.getStatus());
        assertNotNull(order.getCreatedAt());
    }

    @Test
    void createOrder_WithEmptyOrderDishes_ThrowsInvalidDataException() {
        // Arrange
        when(autthenticatePort.getCurrentUserId()).thenReturn(clientId);
        order.setOrderDishes(Collections.emptyList());

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> orderUseCase.createOrder(order));
        assertEquals(OrderUseCaseConstants.ORDER_DISHES_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
        verify(orderPersistencePort, never()).saveOrder(any(Order.class));
    }

    @Test
    void createOrder_WithNullOrderDishes_ThrowsInvalidDataException() {
        // Arrange
        when(autthenticatePort.getCurrentUserId()).thenReturn(clientId);
        order.setOrderDishes(null);

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> orderUseCase.createOrder(order));
        assertEquals(OrderUseCaseConstants.ORDER_DISHES_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
        verify(orderPersistencePort, never()).saveOrder(any(Order.class));
    }

    @Test
    void createOrder_WithNullRestaurantId_ThrowsInvalidDataException() {
        // Arrange
        when(autthenticatePort.getCurrentUserId()).thenReturn(clientId);
        order.getRestaurant().setId(null);

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> orderUseCase.createOrder(order));
        assertEquals(OrderUseCaseConstants.RESTAURANT_ID_CANNOT_BE_NULL, exception.getMessage());
        verify(orderPersistencePort, never()).saveOrder(any(Order.class));
    }

    @Test
    void createOrder_WithNullClientId_ThrowsInvalidDataException() {
        // Arrange
        when(autthenticatePort.getCurrentUserId()).thenReturn(null);

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> orderUseCase.createOrder(order));
        assertEquals(OrderUseCaseConstants.CLIENT_ID_CANNOT_BE_NULL, exception.getMessage());
        verify(orderPersistencePort, never()).saveOrder(any(Order.class));
    }

    @Test
    void createOrder_WithNonExistentRestaurant_ThrowsInvalidDataException() {
        // Arrange
        when(autthenticatePort.getCurrentUserId()).thenReturn(clientId);
        when(restaurantPersistencePort.findById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> orderUseCase.createOrder(order));
        assertEquals(OrderUseCaseConstants.RESTAURANT_NOT_FOUND, exception.getMessage());
        verify(orderPersistencePort, never()).saveOrder(any(Order.class));
    }

    @Test
    void createOrder_WithNonExistentDish_ThrowsInvalidDataException() {
        // Arrange
        when(autthenticatePort.getCurrentUserId()).thenReturn(clientId);
        when(restaurantPersistencePort.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(dishPersistencePort.findById(dishId)).thenReturn(Optional.empty());

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> orderUseCase.createOrder(order));
        assertEquals(OrderUseCaseConstants.DISH_NOT_FOUND, exception.getMessage());
        verify(orderPersistencePort, never()).saveOrder(any(Order.class));
    }

    @Test
    void createOrder_WithDishFromDifferentRestaurant_ThrowsInvalidDataException() {
        // Arrange
        when(autthenticatePort.getCurrentUserId()).thenReturn(clientId);
        when(restaurantPersistencePort.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        Dish dishFromDifferentRestaurant = new Dish();
        dishFromDifferentRestaurant.setId(dishId);
        dishFromDifferentRestaurant.setRestaurantId(999L); // Diferente al restaurantId

        when(dishPersistencePort.findById(dishId)).thenReturn(Optional.of(dishFromDifferentRestaurant));

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> orderUseCase.createOrder(order));
        assertEquals(OrderUseCaseConstants.DISH_NOT_FOUND, exception.getMessage());
        verify(orderPersistencePort, never()).saveOrder(any(Order.class));
    }

    @Test
    void createOrder_WithOrderInProgress_ThrowsResourceConflictException() {
        // Arrange
        when(autthenticatePort.getCurrentUserId()).thenReturn(clientId);
        when(restaurantPersistencePort.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(dishPersistencePort.findById(dishId)).thenReturn(Optional.of(dish));
        when(orderPersistencePort.existsOrderInProgressByClientId(clientId)).thenReturn(true);

        // Act & Assert
        ResourceConflictException exception = assertThrows(ResourceConflictException.class, () -> orderUseCase.createOrder(order));
        assertEquals(OrderUseCaseConstants.ORDER_IN_PROGRESS, exception.getMessage());
        verify(orderPersistencePort, never()).saveOrder(any(Order.class));
    }

}