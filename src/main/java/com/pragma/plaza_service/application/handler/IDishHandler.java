package com.pragma.plaza_service.application.handler;

import com.pragma.plaza_service.application.dto.request.DishCreateDto;
import com.pragma.plaza_service.application.dto.request.DishEditDto;
import com.pragma.plaza_service.application.dto.request.DishListDto;
import com.pragma.plaza_service.application.dto.response.DishResponse;
import com.pragma.plaza_service.application.dto.response.PaginationInfoResponse;

public interface IDishHandler {

    void createDish(DishCreateDto dishCreateDto);
    void modifyDish(DishEditDto dishEditDto, Long idDish);
    void updateDishStatus(Long idDish, boolean status);
    PaginationInfoResponse<DishResponse> listDishesByRestaurantAndCategory(DishListDto dishListDto);

}
