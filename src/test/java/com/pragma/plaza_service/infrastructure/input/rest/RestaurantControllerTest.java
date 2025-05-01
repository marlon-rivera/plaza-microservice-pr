package com.pragma.plaza_service.infrastructure.input.rest;

import com.pragma.plaza_service.application.dto.request.RestaurantCreateDto;
import com.pragma.plaza_service.application.handler.IRestaurantHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantControllerTest {

    @Mock
    private IRestaurantHandler restaurantHandler;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRestaurant_ShouldReturnCreatedStatus() {
        RestaurantCreateDto restaurantCreateDto = new RestaurantCreateDto();
        doNothing().when(restaurantHandler).createRestaurant(restaurantCreateDto);

        ResponseEntity<Void> response = restaurantController.createRestaurant(restaurantCreateDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody());
        verify(restaurantHandler).createRestaurant(restaurantCreateDto);
    }

    @Test
    void validateOwnerRestaurant_ShouldReturnOkStatus() {
        Long restaurantId = 1L;
        boolean isOwner = true;
        when(restaurantHandler.validateRestaurantOwner(restaurantId)).thenReturn(isOwner);

        ResponseEntity<Boolean> response = restaurantController.validateOwnerRestaurant(restaurantId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(isOwner, response.getBody());
        verify(restaurantHandler).validateRestaurantOwner(restaurantId);
    }
}