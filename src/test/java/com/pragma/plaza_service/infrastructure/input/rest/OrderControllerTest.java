package com.pragma.plaza_service.infrastructure.input.rest;

import com.pragma.plaza_service.application.dto.request.DeliverOrderDto;
import com.pragma.plaza_service.application.dto.request.OrderListDto;
import com.pragma.plaza_service.application.dto.request.OrderRequestCreateDto;
import com.pragma.plaza_service.application.dto.response.OrderResponseDto;
import com.pragma.plaza_service.application.dto.response.PaginationInfoResponse;
import com.pragma.plaza_service.application.handler.IOrderHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private IOrderHandler orderHandler;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_ShouldReturnCreatedStatus() {
        // Arrange
        OrderRequestCreateDto orderRequestCreateDto = new OrderRequestCreateDto();
        doNothing().when(orderHandler).createOrder(orderRequestCreateDto);

        // Act
        ResponseEntity<Void> response = orderController.createOrder(orderRequestCreateDto);

        // Assert
        verify(orderHandler, times(1)).createOrder(orderRequestCreateDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllOrdersByStatus_ShouldReturnOkStatusAndOrders() {
        // Arrange
        OrderListDto orderListDto = new OrderListDto();
        PaginationInfoResponse<OrderResponseDto> expectedResponse = new PaginationInfoResponse<>();
        when(orderHandler.getOrdersByStatus(orderListDto)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<PaginationInfoResponse<OrderResponseDto>> response =
                orderController.getAllOrdersByStatus(orderListDto);

        // Assert
        verify(orderHandler, times(1)).getOrdersByStatus(orderListDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void assignOrder_ShouldReturnOkStatus() {
        // Arrange
        Long orderId = 1L;
        doNothing().when(orderHandler).assignOrder(orderId);

        // Act
        ResponseEntity<Void> response = orderController.assignOrder(orderId);

        // Assert
        verify(orderHandler, times(1)).assignOrder(orderId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void finishOrder_ShouldReturnOkStatus() {
        // Arrange
        Long orderId = 1L;
        doNothing().when(orderHandler).finishOrder(orderId);

        // Act
        ResponseEntity<Void> response = orderController.finishOrder(orderId);

        // Assert
        verify(orderHandler, times(1)).finishOrder(orderId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deliverOrder_ShouldReturnOkStatus() {
        // Arrange
        Long orderId = 1L;
        String code = "123456";
        DeliverOrderDto deliverOrderDto = new DeliverOrderDto(orderId, code);
        doNothing().when(orderHandler).deliverOrder(deliverOrderDto);

        // Act
        ResponseEntity<Void> response = orderController.deliverOrder(deliverOrderDto);

        // Assert
        verify(orderHandler, times(1)).deliverOrder(deliverOrderDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
}