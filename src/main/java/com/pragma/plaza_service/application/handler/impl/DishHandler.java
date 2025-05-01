package com.pragma.plaza_service.application.handler.impl;

import com.pragma.plaza_service.application.dto.request.DishCreateDto;
import com.pragma.plaza_service.application.dto.request.DishEditDto;
import com.pragma.plaza_service.application.dto.request.DishListDto;
import com.pragma.plaza_service.application.dto.response.DishResponse;
import com.pragma.plaza_service.application.dto.response.PaginationInfoResponse;
import com.pragma.plaza_service.application.handler.IDishHandler;
import com.pragma.plaza_service.application.mapper.IDishRequestMapper;
import com.pragma.plaza_service.application.mapper.IDishResponseMapper;
import com.pragma.plaza_service.domain.api.IDishServicePort;
import com.pragma.plaza_service.domain.model.Dish;
import com.pragma.plaza_service.domain.model.PaginationInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishHandler implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final IDishResponseMapper dishResponseMapper;

    @Override
    public void createDish(DishCreateDto dishCreateDto) {
        dishServicePort.createDish(
                dishRequestMapper.toDish(dishCreateDto),
                dishCreateDto.getCategory()
        );
    }

    @Override
    public void modifyDish(DishEditDto dishEditDto, Long idDish) {
        dishServicePort.modifyDish(
                idDish,
                dishEditDto.getDescription(),
                dishEditDto.getPrice()
        );
    }

    @Override
    public void updateDishStatus(Long idDish, boolean status) {
        dishServicePort.changeDishStatus(
                idDish,
                status
        );
    }

    @Override
    public PaginationInfoResponse<DishResponse> listDishesByRestaurantAndCategory(DishListDto dishListDto) {
        PaginationInfo<Dish> dishes = dishServicePort.listDishes(dishListDto.getRestaurantId(), dishListDto.getCategoryId(),
                dishListDto.getPageNumber(), dishListDto.getPageSize());
        return dishResponseMapper.toPaginationInfoResponse(
                dishes
        );
    }
}
