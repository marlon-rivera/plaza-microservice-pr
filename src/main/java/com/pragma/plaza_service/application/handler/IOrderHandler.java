package com.pragma.plaza_service.application.handler;

import com.pragma.plaza_service.application.dto.request.OrderRequestCreateDto;

public interface IOrderHandler {

    void createOrder(OrderRequestCreateDto orderRequestCreateDto);
}
