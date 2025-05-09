package com.pragma.plaza_service.domain.spi;

import com.pragma.plaza_service.domain.model.Restaurant;

public interface IRestaurantPersistencePort {

    void saveRestaurant(Restaurant restaurant);
    boolean existsByNit(String nit);

}
