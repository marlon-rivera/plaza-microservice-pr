package com.pragma.plaza_service.infrastructure.out.jpa.mapper;

import com.pragma.plaza_service.domain.model.Order;
import com.pragma.plaza_service.domain.model.OrderDish;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IOrderEntityMapper {

    @Mapping(target = "restaurantEntity.id", source = "order.restaurant.id")
    OrderEntity toEntity(Order order);

    @Mapping(target = "order.id", ignore = true)
    @Mapping(target = "dishEntity.id", source = "orderDish.dish.id")
    OrderDishEntity toOrderDishEntity(OrderDish orderDish);

}
