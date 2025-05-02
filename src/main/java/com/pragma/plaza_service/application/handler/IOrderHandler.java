package com.pragma.plaza_service.application.handler;

import com.pragma.plaza_service.application.dto.request.OrderListDto;
import com.pragma.plaza_service.application.dto.request.OrderRequestCreateDto;
import com.pragma.plaza_service.application.dto.response.OrderResponseDto;
import com.pragma.plaza_service.application.dto.response.PaginationInfoResponse;

public interface IOrderHandler {

    void createOrder(OrderRequestCreateDto orderRequestCreateDto);
    PaginationInfoResponse<OrderResponseDto> getOrdersByStatus(OrderListDto orderListDto);
    void assignOrder(Long orderId);
}
