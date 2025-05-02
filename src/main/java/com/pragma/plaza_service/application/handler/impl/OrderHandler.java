package com.pragma.plaza_service.application.handler.impl;

import com.pragma.plaza_service.application.dto.request.OrderRequestCreateDto;
import com.pragma.plaza_service.application.handler.IOrderHandler;
import com.pragma.plaza_service.application.mapper.IOrderRequestMapper;
import com.pragma.plaza_service.domain.api.IOrderServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;

    @Override
    public void createOrder(OrderRequestCreateDto orderRequestCreateDto) {
        orderServicePort.createOrder(
                orderRequestMapper.toOrder(orderRequestCreateDto)
        );
    }
}
