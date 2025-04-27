package com.pragma.plaza_service.infrastructure.out.jpa.mapper;

import com.pragma.plaza_service.domain.model.DishCategory;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.DishCategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IDishCategoryEntityMapper {

    DishCategory toDishCategory(DishCategoryEntity dishCategoryEntity);
    default Optional<DishCategory> toDishCategoryOptional(Optional<DishCategoryEntity> dishCategoryEntity) {
        return dishCategoryEntity.map(this::toDishCategory);
    }

}
