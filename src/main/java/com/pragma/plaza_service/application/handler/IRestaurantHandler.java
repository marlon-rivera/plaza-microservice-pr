package com.pragma.plaza_service.application.handler;

import com.pragma.plaza_service.application.dto.request.RestaurantCreateDto;

public interface IRestaurantHandler {

    void createRestaurant(RestaurantCreateDto restaurantCreateDto);

}
