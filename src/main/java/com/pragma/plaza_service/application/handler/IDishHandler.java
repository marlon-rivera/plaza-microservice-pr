package com.pragma.plaza_service.application.handler;

import com.pragma.plaza_service.application.dto.request.DishCreateDto;
import com.pragma.plaza_service.application.dto.request.DishEditDto;

public interface IDishHandler {

    void createDish(DishCreateDto dishCreateDto);
    void modifyDish(DishEditDto dishEditDto, Long idDish);
    void updateDishStatus(Long idDish, boolean status);

}
