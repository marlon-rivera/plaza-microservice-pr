package com.pragma.plaza_service.infrastructure.input.rest;

import com.pragma.plaza_service.application.dto.request.DishCreateDto;
import com.pragma.plaza_service.application.handler.IDishHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishControllerTest {

    @Mock
    private IDishHandler dishHandler;

    @InjectMocks
    private DishController dishController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDish_ShouldReturnCreatedStatus() {
        // Arrange
        DishCreateDto dishCreateDto = new DishCreateDto();
        doNothing().when(dishHandler).createDish(dishCreateDto);

        // Act
        ResponseEntity<Void> response = dishController.createDish(dishCreateDto);

        // Assert
        verify(dishHandler, times(1)).createDish(dishCreateDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());
    }
}