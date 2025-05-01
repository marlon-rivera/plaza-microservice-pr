package com.pragma.plaza_service.domain.usecase;

import com.pragma.plaza_service.domain.api.IRestaurantServicePort;
import com.pragma.plaza_service.domain.exception.ResourceConflictException;
import com.pragma.plaza_service.domain.exception.UserNotOwnerException;
import com.pragma.plaza_service.domain.model.PaginationInfo;
import com.pragma.plaza_service.domain.model.Restaurant;
import com.pragma.plaza_service.domain.spi.IAutthenticatePort;
import com.pragma.plaza_service.domain.spi.IRestaurantPersistencePort;
import com.pragma.plaza_service.domain.spi.IUserPersistencePort;
import com.pragma.plaza_service.domain.util.constants.RestaurantUseCaseConstants;
import com.pragma.plaza_service.domain.util.validators.RestaurantValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserPersistencePort userPersistencePort;
    private final IAutthenticatePort autthenticatePort;

    @Override
    public void createRestaurant(Restaurant restaurant) {
        if (!userPersistencePort.isOwner(restaurant.getOwnerId())) {
            throw new UserNotOwnerException();
        }
        RestaurantValidator.validateRestaurant(restaurant);
        if(!restaurantPersistencePort.existsByNit(restaurant.getNit())) {
            restaurantPersistencePort.saveRestaurant(restaurant);
        } else {
            throw new ResourceConflictException(RestaurantUseCaseConstants.RESTAURANT_WITH_NIT_ALREADY_EXISTS);
        }
    }

    @Override
    public boolean validateOwnerRestaurant(Long restaurantId) {
        Long id = autthenticatePort.getCurrentUserId();
        Long ownerId = restaurantPersistencePort.findOwnerIdByRestaurantId(restaurantId);
        return id.equals(ownerId);
    }

    @Override
    public PaginationInfo<Restaurant> listRestaurants(int page, int sizePage) {
        return restaurantPersistencePort.listRestaurants(page, sizePage);
    }
}
