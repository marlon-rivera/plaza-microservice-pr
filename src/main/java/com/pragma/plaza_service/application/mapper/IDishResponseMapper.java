package com.pragma.plaza_service.application.mapper;

import com.pragma.plaza_service.application.dto.response.DishResponse;
import com.pragma.plaza_service.application.dto.response.PaginationInfoResponse;
import com.pragma.plaza_service.domain.model.Dish;
import com.pragma.plaza_service.domain.model.PaginationInfo;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface IDishResponseMapper {

    PaginationInfoResponse<DishResponse> toPaginationInfoResponse(PaginationInfo<Dish> paginationInfo);

}
