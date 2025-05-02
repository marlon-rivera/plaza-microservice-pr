package com.pragma.plaza_service.domain.api;

import com.pragma.plaza_service.domain.model.Order;
import com.pragma.plaza_service.domain.model.PaginationInfo;

public interface IOrderServicePort {

    void createOrder(Order order);
    PaginationInfo<Order> getOrdersByRestaurantIdAndStatus(String status, int page, int size);

}
