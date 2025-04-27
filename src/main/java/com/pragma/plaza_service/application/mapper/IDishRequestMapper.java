package com.pragma.plaza_service.application.mapper;

import com.pragma.plaza_service.application.dto.request.DishCreateDto;
import com.pragma.plaza_service.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IDishRequestMapper {

    @Mapping(target = "category", ignore = true)
    Dish toDish(DishCreateDto dishCreateDto);

}
