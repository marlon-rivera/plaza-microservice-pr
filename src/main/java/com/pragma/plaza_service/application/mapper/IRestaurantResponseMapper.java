package com.pragma.plaza_service.application.mapper;

import com.pragma.plaza_service.application.dto.response.PaginationInfoResponse;
import com.pragma.plaza_service.application.dto.response.RestaurantResponseDto;
import com.pragma.plaza_service.domain.model.PaginationInfo;
import com.pragma.plaza_service.domain.model.Restaurant;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface IRestaurantResponseMapper {

    PaginationInfoResponse<RestaurantResponseDto> toPaginationInfoResponse(PaginationInfo<Restaurant> paginationInfo);

}
