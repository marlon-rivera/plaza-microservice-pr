package com.pragma.plaza_service.application.handler;

import com.pragma.plaza_service.application.dto.request.RestaurantCreateDto;
import com.pragma.plaza_service.application.dto.response.PaginationInfoResponse;
import com.pragma.plaza_service.application.dto.response.RestaurantResponseDto;

public interface IRestaurantHandler {

    void createRestaurant(RestaurantCreateDto restaurantCreateDto);
    boolean validateRestaurantOwner(Long restaurantId);
    PaginationInfoResponse<RestaurantResponseDto> listRestaurants(int size, int page);
}
