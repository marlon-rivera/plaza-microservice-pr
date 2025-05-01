package com.pragma.plaza_service.infrastructure.out.jpa.mapper;

import com.pragma.plaza_service.domain.model.Restaurant;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Optional;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantEntityMapper {

    RestaurantEntity toRestaurantEntity(Restaurant restaurant);
    List<Restaurant> toRestaurantList(List<RestaurantEntity> restaurantEntities);
    Restaurant toRestaurant(RestaurantEntity restaurantEntity);
    default Optional<Restaurant> toRestaurantOptional(Optional<RestaurantEntity> restaurantEntity) {
        return restaurantEntity.map(this::toRestaurant);
    }

}
