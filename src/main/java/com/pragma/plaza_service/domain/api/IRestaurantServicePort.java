package com.pragma.plaza_service.domain.api;

import com.pragma.plaza_service.domain.model.Restaurant;

public interface IRestaurantServicePort {

    void createRestaurant(Restaurant restaurant);
    boolean validateOwnerRestaurant(Long restaurantId);

}
