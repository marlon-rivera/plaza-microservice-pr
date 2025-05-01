package com.pragma.plaza_service.application.handler.impl;

import com.pragma.plaza_service.application.dto.request.RestaurantCreateDto;
import com.pragma.plaza_service.application.handler.IRestaurantHandler;
import com.pragma.plaza_service.application.mapper.IRestaurantRequestMapper;
import com.pragma.plaza_service.domain.api.IRestaurantServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantServicePort restaurantServicePort;

    @Override
    public void createRestaurant(RestaurantCreateDto restaurantCreateDto) {
        restaurantServicePort.createRestaurant(restaurantRequestMapper.toRestaurant(restaurantCreateDto));
    }

    @Override
    public boolean validateRestaurantOwner(Long restaurantId) {
        return restaurantServicePort.validateOwnerRestaurant(restaurantId);
    }

}
