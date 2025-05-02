package com.pragma.plaza_service.application.mapper;

import com.pragma.plaza_service.application.dto.request.OrderDishRequestCreateDto;
import com.pragma.plaza_service.application.dto.request.OrderRequestCreateDto;
import com.pragma.plaza_service.domain.model.Order;
import com.pragma.plaza_service.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface IOrderRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "restaurant.id", source = "restaurantId")
    @Mapping(target = "clientId", ignore = true)
    Order toOrder(OrderRequestCreateDto orderRequestCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dish.id", source = "dishId")
    @Mapping(target = "order", ignore = true)
    OrderDish toOrderDish(OrderDishRequestCreateDto orderDishRequestCreateDto);
}
