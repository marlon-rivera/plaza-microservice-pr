package com.pragma.plaza_service.domain.usecase;

import com.pragma.plaza_service.domain.api.IOrderServicePort;
import com.pragma.plaza_service.domain.exception.InvalidDataException;
import com.pragma.plaza_service.domain.exception.ResourceConflictException;
import com.pragma.plaza_service.domain.model.*;
import com.pragma.plaza_service.domain.spi.*;
import com.pragma.plaza_service.domain.util.constants.OrderUseCaseConstants;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IAutthenticatePort autthenticatePort;
    private final IUserPersistencePort userPersistencePort;

    @Override
    public void createOrder(Order order) {
        Long idClient = autthenticatePort.getCurrentUserId();
        order.setClientId(idClient);
        validateOrder(order);
        order.setStatus(StatusOrderEnum.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        orderPersistencePort.saveOrder(order);
    }

    @Override
    public PaginationInfo<Order> getOrdersByRestaurantIdAndStatus(String status, int page, int size) {
        Long restaurantId = userPersistencePort.getIdRestaurantByIdEmployee();
        if (restaurantId == null) {
            throw new InvalidDataException(OrderUseCaseConstants.EMPLOYEE_NOT_BELONG_TO_RESTAURANT);
        }
        return orderPersistencePort.getOrdersByIdRestaurantAndStatus(restaurantId, status, page, size);
    }

    private void validateOrder(Order order) {
        if (order.getOrderDishes() == null || order.getOrderDishes().isEmpty()) {
            throw new InvalidDataException(OrderUseCaseConstants.ORDER_DISHES_CANNOT_BE_NULL_OR_EMPTY);
        }

        if (order.getClientId() == null) {
            throw new InvalidDataException(OrderUseCaseConstants.CLIENT_ID_CANNOT_BE_NULL);
        }

        if (order.getRestaurant().getId() == null) {
            throw new InvalidDataException(OrderUseCaseConstants.RESTAURANT_ID_CANNOT_BE_NULL);
        }
        validateRestaurantId(order.getRestaurant().getId());
        validateDishes(order.getOrderDishes(), order.getRestaurant().getId());
        validateOrderInProgress(order.getClientId());
    }

    private void validateOrderInProgress(Long clientId) {
        if (orderPersistencePort.existsOrderInProgressByClientId(clientId)) {
            throw new ResourceConflictException(OrderUseCaseConstants.ORDER_IN_PROGRESS);
        }
    }

    private void validateRestaurantId(Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantPersistencePort.findById(restaurantId);
        if (restaurant.isEmpty()) {
            throw new InvalidDataException(OrderUseCaseConstants.RESTAURANT_NOT_FOUND);
        }
    }

    private void validateDishes(List<OrderDish> orderDishes, Long restaurantId) {
        for (OrderDish orderDish : orderDishes) {
            Optional<Dish> dish = dishPersistencePort.findById(orderDish.getDish().getId());
            if (dish.isEmpty()) {
                throw new InvalidDataException(OrderUseCaseConstants.DISH_NOT_FOUND);
            }
            if (!dish.get().getRestaurantId().equals(restaurantId)) {
                throw new InvalidDataException(OrderUseCaseConstants.DISH_NOT_FOUND);
            }
        }
    }
}
