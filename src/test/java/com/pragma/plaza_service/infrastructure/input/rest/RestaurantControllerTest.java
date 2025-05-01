package com.pragma.plaza_service.infrastructure.input.rest;

import com.pragma.plaza_service.application.dto.request.RestaurantCreateDto;
import com.pragma.plaza_service.application.dto.response.PaginationInfoResponse;
import com.pragma.plaza_service.application.dto.response.RestaurantResponseDto;
import com.pragma.plaza_service.application.handler.IRestaurantHandler;
import com.pragma.plaza_service.domain.model.PaginationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

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

    @Test
    void listRestaurants_ShouldReturnOkStatusWithListOfRestaurants() {
        int page = 0;
        int sizePage = 10;
        PaginationInfoResponse<RestaurantResponseDto> paginationInfo = new PaginationInfoResponse<>(
                new PaginationInfo<>(List.of(),
                page,
                sizePage,
                0,
                0,
                false,
                false
            )
        );
        when(restaurantHandler.listRestaurants(page, sizePage)).thenReturn(paginationInfo);

        ResponseEntity<PaginationInfoResponse<RestaurantResponseDto>> response = restaurantController.listRestaurants(page, sizePage);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paginationInfo, response.getBody());
        verify(restaurantHandler).listRestaurants(page, sizePage);
    }
}