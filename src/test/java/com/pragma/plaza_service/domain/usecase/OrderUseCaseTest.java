package com.pragma.plaza_service.domain.usecase;

import com.pragma.plaza_service.domain.exception.InvalidDataException;
import com.pragma.plaza_service.domain.exception.ResourceConflictException;
import com.pragma.plaza_service.domain.exception.ResourceNotFoundException;
import com.pragma.plaza_service.domain.model.*;
import com.pragma.plaza_service.domain.spi.*;
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

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private INotificationPersistencePort notificationPersistencePort;

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

    @Test
    void getOrdersByRestaurantIdAndStatus_Successfully() {
        // Arrange
        String status = "PENDING";
        int page = 0;
        int size = 10;
        Long restaurantId = 2L;
        PaginationInfo<Order> expectedPaginationInfo = new PaginationInfo<>(
                List.of(),
                0,
                0,
                0,
                0,
                false,
                false
        );

        when(userPersistencePort.getIdRestaurantByIdEmployee()).thenReturn(restaurantId);
        when(orderPersistencePort.getOrdersByIdRestaurantAndStatus(restaurantId, status, page, size))
                .thenReturn(expectedPaginationInfo);

        // Act
        PaginationInfo<Order> result = orderUseCase.getOrdersByRestaurantIdAndStatus(status, page, size);

        // Assert
        assertEquals(expectedPaginationInfo, result);
        verify(userPersistencePort).getIdRestaurantByIdEmployee();
        verify(orderPersistencePort).getOrdersByIdRestaurantAndStatus(restaurantId, status, page, size);
    }

    @Test
    void getOrdersByRestaurantIdAndStatus_WithNullRestaurantId_ThrowsInvalidDataException() {
        // Arrange
        String status = "PENDING";
        int page = 0;
        int size = 10;

        when(userPersistencePort.getIdRestaurantByIdEmployee()).thenReturn(null);

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> orderUseCase.getOrdersByRestaurantIdAndStatus(status, page, size));
        assertEquals(OrderUseCaseConstants.EMPLOYEE_NOT_BELONG_TO_RESTAURANT, exception.getMessage());
        verify(userPersistencePort).getIdRestaurantByIdEmployee();
        verify(orderPersistencePort, never()).getOrdersByIdRestaurantAndStatus(any(), any(), anyInt(), anyInt());
    }

    @Test
    void assignOrder_Successfully() {
        // Arrange
        Long orderId = 1L;
        Long employeeId = 5L;
        Long restaurantId = 2L;

        Order order = new Order();
        order.setStatus(StatusOrderEnum.PENDING);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        order.setRestaurant(restaurant);

        when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(order));
        when(userPersistencePort.getIdRestaurantByIdEmployee()).thenReturn(restaurantId);
        when(autthenticatePort.getCurrentUserId()).thenReturn(employeeId);

        // Act
        orderUseCase.assignOrder(orderId);

        // Assert
        assertEquals(StatusOrderEnum.IN_PROGRESS, order.getStatus());
        assertEquals(employeeId, order.getIdEmployee());
        verify(orderPersistencePort).findById(orderId);
        verify(userPersistencePort).getIdRestaurantByIdEmployee();
        verify(autthenticatePort).getCurrentUserId();
        verify(orderPersistencePort).updateOrder(order);
    }

    @Test
    void assignOrder_WithNonExistentOrder_ThrowsResourceNotFoundException() {
        // Arrange
        Long orderId = 1L;

        when(orderPersistencePort.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> orderUseCase.assignOrder(orderId));
        assertEquals(OrderUseCaseConstants.ORDER_NOT_FOUND, exception.getMessage());
        verify(orderPersistencePort).findById(orderId);
        verify(userPersistencePort, never()).getIdRestaurantByIdEmployee();
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void assignOrder_WithNonPendingOrder_ThrowsInvalidDataException() {
        // Arrange
        Long orderId = 1L;

        Order order = new Order();
        order.setStatus(StatusOrderEnum.IN_PROGRESS); // No es PENDING

        when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(order));

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> orderUseCase.assignOrder(orderId));
        assertEquals(OrderUseCaseConstants.ORDER_NOT_PENDING, exception.getMessage());
        verify(orderPersistencePort).findById(orderId);
        verify(userPersistencePort, never()).getIdRestaurantByIdEmployee();
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void assignOrder_WithOrderFromDifferentRestaurant_ThrowsInvalidDataException() {
        // Arrange
        Long orderId = 1L;
        Long employeeRestaurantId = 2L;
        Long orderRestaurantId = 3L; // Diferente al restaurante del empleado

        Order order = new Order();
        order.setStatus(StatusOrderEnum.PENDING);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(orderRestaurantId);
        order.setRestaurant(restaurant);

        when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(order));
        when(userPersistencePort.getIdRestaurantByIdEmployee()).thenReturn(employeeRestaurantId);

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> orderUseCase.assignOrder(orderId));
        assertEquals(OrderUseCaseConstants.ORDER_NOT_BELONG_TO_RESTAURANT, exception.getMessage());
        verify(orderPersistencePort).findById(orderId);
        verify(userPersistencePort).getIdRestaurantByIdEmployee();
        verify(autthenticatePort, never()).getCurrentUserId();
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void finishOrder_Successfully() {
        // Arrange
        Long orderId = 1L;
        Long employeeRestaurantId = 2L;
        Long clientId = 3L;
        String phoneNumber = "+1234567890";

        Order order = new Order();
        order.setStatus(StatusOrderEnum.IN_PROGRESS);
        order.setClientId(clientId);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(employeeRestaurantId);
        order.setRestaurant(restaurant);

        when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(order));
        when(userPersistencePort.getIdRestaurantByIdEmployee()).thenReturn(employeeRestaurantId);
        when(userPersistencePort.getPhoneNumberByIdClient(clientId)).thenReturn(phoneNumber);
        doNothing().when(notificationPersistencePort).sendNotification(orderId, phoneNumber);

        // Act
        orderUseCase.finishOrder(orderId);

        // Assert
        assertEquals(StatusOrderEnum.READY, order.getStatus());
        verify(orderPersistencePort).findById(orderId);
        verify(userPersistencePort).getIdRestaurantByIdEmployee();
        verify(userPersistencePort).getPhoneNumberByIdClient(clientId);
        verify(notificationPersistencePort).sendNotification(orderId, phoneNumber);
        verify(orderPersistencePort).updateOrder(order);
    }

    @Test
    void finishOrder_WithNonExistentOrder_ThrowsResourceNotFoundException() {
        // Arrange
        Long orderId = 1L;
        when(orderPersistencePort.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> orderUseCase.finishOrder(orderId));
        assertEquals(OrderUseCaseConstants.ORDER_NOT_FOUND, exception.getMessage());
        verify(orderPersistencePort).findById(orderId);
        verify(userPersistencePort, never()).getIdRestaurantByIdEmployee();
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void finishOrder_WithOrderFromDifferentRestaurant_ThrowsInvalidDataException() {
        // Arrange
        Long orderId = 1L;
        Long employeeRestaurantId = 2L;
        Long orderRestaurantId = 3L; // Diferente al restaurante del empleado

        Order order = new Order();
        order.setStatus(StatusOrderEnum.IN_PROGRESS);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(orderRestaurantId);
        order.setRestaurant(restaurant);

        when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(order));
        when(userPersistencePort.getIdRestaurantByIdEmployee()).thenReturn(employeeRestaurantId);

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> orderUseCase.finishOrder(orderId));
        assertEquals(OrderUseCaseConstants.ORDER_NOT_BELONG_TO_RESTAURANT, exception.getMessage());
        verify(orderPersistencePort).findById(orderId);
        verify(userPersistencePort).getIdRestaurantByIdEmployee();
        verify(userPersistencePort, never()).getPhoneNumberByIdClient(any());
        verify(notificationPersistencePort, never()).sendNotification(any(), any());
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void finishOrder_WithOrderNotInProgress_ThrowsInvalidDataException() {
        // Arrange
        Long orderId = 1L;
        Long restaurantId = 2L;

        Order order = new Order();
        order.setStatus(StatusOrderEnum.PENDING); // No está en progreso
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        order.setRestaurant(restaurant);

        when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(order));
        when(userPersistencePort.getIdRestaurantByIdEmployee()).thenReturn(restaurantId);

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> orderUseCase.finishOrder(orderId));
        assertEquals(OrderUseCaseConstants.ORDER_NOT_IN_PROGRESS, exception.getMessage());
        verify(orderPersistencePort).findById(orderId);
        verify(userPersistencePort).getIdRestaurantByIdEmployee();
        verify(userPersistencePort, never()).getPhoneNumberByIdClient(any());
        verify(notificationPersistencePort, never()).sendNotification(any(), any());
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void finishOrder_WithNullPhoneNumber_ThrowsInvalidDataException() {
        // Arrange
        Long orderId = 1L;
        Long restaurantId = 2L;
        Long clientId = 3L;

        Order order = new Order();
        order.setStatus(StatusOrderEnum.IN_PROGRESS);
        order.setClientId(clientId);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        order.setRestaurant(restaurant);

        when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(order));
        when(userPersistencePort.getIdRestaurantByIdEmployee()).thenReturn(restaurantId);
        when(userPersistencePort.getPhoneNumberByIdClient(clientId)).thenReturn(null);

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> orderUseCase.finishOrder(orderId));
        assertEquals(OrderUseCaseConstants.PHONE_NUMBER_NOT_FOUND, exception.getMessage());
        verify(orderPersistencePort).findById(orderId);
        verify(userPersistencePort).getIdRestaurantByIdEmployee();
        verify(userPersistencePort).getPhoneNumberByIdClient(clientId);
        verify(notificationPersistencePort, never()).sendNotification(any(), any());
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void deliverOrder_Successfully() {
        // Arrange
        Long orderId = 1L;
        String code = "123456";
        Long restaurantId = 2L;

        Order order = new Order();
        order.setStatus(StatusOrderEnum.READY);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        order.setRestaurant(restaurant);

        when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(order));
        when(userPersistencePort.getIdRestaurantByIdEmployee()).thenReturn(restaurantId);
        when(notificationPersistencePort.validateConfirmationCode(orderId, code)).thenReturn(true);

        // Act
        orderUseCase.deliverOrder(orderId, code);

        // Assert
        assertEquals(StatusOrderEnum.DELIVERED, order.getStatus());
        verify(orderPersistencePort).findById(orderId);
        verify(userPersistencePort).getIdRestaurantByIdEmployee();
        verify(notificationPersistencePort).validateConfirmationCode(orderId, code);
        verify(orderPersistencePort).updateOrder(order);
    }

    @Test
    void deliverOrder_WithNonExistentOrder_ThrowsResourceNotFoundException() {
        // Arrange
        Long orderId = 1L;
        String code = "123456";

        when(orderPersistencePort.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> orderUseCase.deliverOrder(orderId, code));
        assertEquals(OrderUseCaseConstants.ORDER_NOT_FOUND, exception.getMessage());
        verify(orderPersistencePort).findById(orderId);
        verify(userPersistencePort, never()).getIdRestaurantByIdEmployee();
        verify(notificationPersistencePort, never()).validateConfirmationCode(any(), any());
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void deliverOrder_WithOrderFromDifferentRestaurant_ThrowsInvalidDataException() {
        // Arrange
        Long orderId = 1L;
        String code = "123456";
        Long employeeRestaurantId = 2L;
        Long orderRestaurantId = 3L; // Diferente al restaurante del empleado

        Order order = new Order();
        order.setStatus(StatusOrderEnum.READY);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(orderRestaurantId);
        order.setRestaurant(restaurant);

        when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(order));
        when(userPersistencePort.getIdRestaurantByIdEmployee()).thenReturn(employeeRestaurantId);

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> orderUseCase.deliverOrder(orderId, code));
        assertEquals(OrderUseCaseConstants.ORDER_NOT_BELONG_TO_RESTAURANT, exception.getMessage());
        verify(orderPersistencePort).findById(orderId);
        verify(userPersistencePort).getIdRestaurantByIdEmployee();
        verify(notificationPersistencePort, never()).validateConfirmationCode(any(), any());
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void deliverOrder_WithOrderNotReady_ThrowsInvalidDataException() {
        // Arrange
        Long orderId = 1L;
        String code = "123456";
        Long restaurantId = 2L;

        Order order = new Order();
        order.setStatus(StatusOrderEnum.IN_PROGRESS); // No está en estado READY
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        order.setRestaurant(restaurant);

        when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(order));
        when(userPersistencePort.getIdRestaurantByIdEmployee()).thenReturn(restaurantId);

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> orderUseCase.deliverOrder(orderId, code));
        assertEquals(OrderUseCaseConstants.ORDER_NOT_READY, exception.getMessage());
        verify(orderPersistencePort).findById(orderId);
        verify(userPersistencePort).getIdRestaurantByIdEmployee();
        verify(notificationPersistencePort, never()).validateConfirmationCode(any(), any());
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void deliverOrder_WithInvalidCode_ThrowsInvalidDataException() {
        // Arrange
        Long orderId = 1L;
        String code = "123456";
        Long restaurantId = 2L;

        Order order = new Order();
        order.setStatus(StatusOrderEnum.READY);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        order.setRestaurant(restaurant);

        when(orderPersistencePort.findById(orderId)).thenReturn(Optional.of(order));
        when(userPersistencePort.getIdRestaurantByIdEmployee()).thenReturn(restaurantId);
        when(notificationPersistencePort.validateConfirmationCode(orderId, code)).thenReturn(false);

        // Act & Assert
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> orderUseCase.deliverOrder(orderId, code));
        assertEquals(OrderUseCaseConstants.CODE_NOT_VALID, exception.getMessage());
        verify(orderPersistencePort).findById(orderId);
        verify(userPersistencePort).getIdRestaurantByIdEmployee();
        verify(notificationPersistencePort).validateConfirmationCode(orderId, code);
        verify(orderPersistencePort, never()).updateOrder(any());
    }
}