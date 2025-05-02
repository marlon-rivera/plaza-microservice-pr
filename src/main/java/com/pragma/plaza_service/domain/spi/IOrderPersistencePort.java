package com.pragma.plaza_service.domain.spi;

import com.pragma.plaza_service.domain.model.Order;
import com.pragma.plaza_service.domain.model.PaginationInfo;

public interface IOrderPersistencePort {

    void saveOrder(Order order);
    boolean existsOrderInProgressByClientId(Long clientId);
    PaginationInfo<Order> getOrdersByIdRestaurantAndStatus(Long idRestaurant, String status, int page, int size);

}
