package com.pragma.plaza_service.application.handler.impl;

import com.pragma.plaza_service.application.dto.request.DishCreateDto;
import com.pragma.plaza_service.application.handler.IDishHandler;
import com.pragma.plaza_service.application.mapper.IDishRequestMapper;
import com.pragma.plaza_service.domain.api.IDishServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishHandler implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;

    @Override
    public void createDish(DishCreateDto dishCreateDto) {
        dishServicePort.createDish(
                dishRequestMapper.toDish(dishCreateDto),
                dishCreateDto.getCategory()
        );
    }
}
