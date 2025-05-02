package com.pragma.plaza_service.domain.api;

import com.pragma.plaza_service.domain.model.Order;

public interface IOrderServicePort {

    void createOrder(Order order);

}
