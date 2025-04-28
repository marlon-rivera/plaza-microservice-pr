package com.pragma.plaza_service.infrastructure.out.jpa.mapper;

import com.pragma.plaza_service.domain.model.Dish;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IDishEntityMapper {

    DishEntity toDishEntity(Dish dish);

    Dish toDish(DishEntity dishEntity);

    default Optional<Dish> toOptionalDish(Optional<DishEntity> dishEntity) {
        return dishEntity.map(this::toDish);
    }

}
