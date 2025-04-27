package com.pragma.plaza_service.application.mapper;

import com.pragma.plaza_service.application.dto.request.RestaurantCreateDto;
import com.pragma.plaza_service.domain.model.Restaurant;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface IRestaurantRequestMapper {

    Restaurant toRestaurant(RestaurantCreateDto restaurantCreateDto);

}
