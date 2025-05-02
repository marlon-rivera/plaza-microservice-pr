package com.pragma.plaza_service.infrastructure.input.rest;

import com.pragma.plaza_service.application.dto.request.OrderRequestCreateDto;
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

}