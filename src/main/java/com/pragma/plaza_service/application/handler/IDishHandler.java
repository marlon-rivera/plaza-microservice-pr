package com.pragma.plaza_service.application.handler;

import com.pragma.plaza_service.application.dto.request.DishCreateDto;

public interface IDishHandler {

    void createDish(DishCreateDto dishCreateDto);

}
