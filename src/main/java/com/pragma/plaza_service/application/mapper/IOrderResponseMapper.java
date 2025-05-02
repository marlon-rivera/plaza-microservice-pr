package com.pragma.plaza_service.application.mapper;

import com.pragma.plaza_service.application.dto.response.OrderResponseDto;
import com.pragma.plaza_service.application.dto.response.PaginationInfoResponse;
import com.pragma.plaza_service.domain.model.Order;
import com.pragma.plaza_service.domain.model.PaginationInfo;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface IOrderResponseMapper {

    PaginationInfoResponse<OrderResponseDto> toPaginationInfoResponse(PaginationInfo<Order> paginationInfo);

}
