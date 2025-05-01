package com.pragma.plaza_service.infrastructure.input.rest;

import com.pragma.plaza_service.application.dto.request.DishCreateDto;
import com.pragma.plaza_service.application.dto.request.DishEditDto;
import com.pragma.plaza_service.application.dto.request.DishListDto;
import com.pragma.plaza_service.application.dto.request.DishUpdateStatusDto;
import com.pragma.plaza_service.application.dto.response.DishResponse;
import com.pragma.plaza_service.application.dto.response.PaginationInfoResponse;
import com.pragma.plaza_service.application.handler.IDishHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

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

    @Test
    void modifyDish_ShouldReturnOkStatus() {
        // Arrange
        Long dishId = 1L;
        String description = "New Description";
        BigDecimal price = new BigDecimal("10.00");
        DishEditDto dishEditDto = new DishEditDto();
        dishEditDto.setDescription(description);
        dishEditDto.setPrice(price);
        doNothing().when(dishHandler).modifyDish(dishEditDto, dishId);

        // Act
        ResponseEntity<Void> response = dishController.editDish(dishId, dishEditDto);

        // Assert
        verify(dishHandler, times(1)).modifyDish(dishEditDto, dishId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void changeDishStatus_ShouldReturnOkStatus() {
        // Arrange
        Long dishId = 1L;
        boolean status = true;
        doNothing().when(dishHandler).updateDishStatus(dishId, status);

        // Act
        ResponseEntity<Void> response = dishController.changeDishStatus(dishId, new DishUpdateStatusDto(true));

        // Assert
        verify(dishHandler, times(1)).updateDishStatus(dishId, status);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void listDishes_ShouldReturnOkStatus() {
        // Arrange
        DishListDto dishListDto = new DishListDto();
        PaginationInfoResponse<DishResponse> paginationResponse = new PaginationInfoResponse<>();
        when(dishHandler.listDishesByRestaurantAndCategory(dishListDto)).thenReturn(paginationResponse);

        // Act
        ResponseEntity<PaginationInfoResponse<DishResponse>> response = dishController.listDishes(dishListDto);

        // Assert
        verify(dishHandler, times(1)).listDishesByRestaurantAndCategory(dishListDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paginationResponse, response.getBody());
    }

}