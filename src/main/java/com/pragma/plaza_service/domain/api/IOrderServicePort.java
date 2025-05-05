package com.pragma.plaza_service.domain.api;

import com.pragma.plaza_service.domain.model.Order;
import com.pragma.plaza_service.domain.model.PaginationInfo;

public interface IOrderServicePort {

    void createOrder(Order order);
    PaginationInfo<Order> getOrdersByRestaurantIdAndStatus(String status, int page, int size);
    void assignOrder(Long orderId);
    void finishOrder(Long orderId);
    void deliverOrder(Long orderId, String code);
    void cancelOrder(Long orderId);

}
