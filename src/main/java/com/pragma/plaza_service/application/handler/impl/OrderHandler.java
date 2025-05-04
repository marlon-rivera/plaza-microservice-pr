package com.pragma.plaza_service.application.handler.impl;

import com.pragma.plaza_service.application.dto.request.OrderListDto;
import com.pragma.plaza_service.application.dto.request.OrderRequestCreateDto;
import com.pragma.plaza_service.application.dto.response.OrderResponseDto;
import com.pragma.plaza_service.application.dto.response.PaginationInfoResponse;
import com.pragma.plaza_service.application.handler.IOrderHandler;
import com.pragma.plaza_service.application.mapper.IOrderRequestMapper;
import com.pragma.plaza_service.application.mapper.IOrderResponseMapper;
import com.pragma.plaza_service.domain.api.IOrderServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderResponseMapper orderResponseMapper;

    @Override
    public void createOrder(OrderRequestCreateDto orderRequestCreateDto) {
        orderServicePort.createOrder(
                orderRequestMapper.toOrder(orderRequestCreateDto)
        );
    }

    @Override
    public PaginationInfoResponse<OrderResponseDto> getOrdersByStatus(OrderListDto orderListDto) {
        return orderResponseMapper.toPaginationInfoResponse(
                orderServicePort.getOrdersByRestaurantIdAndStatus(orderListDto.getStatus().name(),
                        orderListDto.getPage(), orderListDto.getSize())
        );
    }

    @Override
    public void assignOrder(Long orderId) {
        orderServicePort.assignOrder(orderId);
    }

    @Override
    public void finishOrder(Long orderId) {
        orderServicePort.finishOrder(orderId);
    }
}
