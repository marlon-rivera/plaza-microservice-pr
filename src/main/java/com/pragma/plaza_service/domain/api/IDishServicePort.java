package com.pragma.plaza_service.domain.api;

import com.pragma.plaza_service.domain.model.Dish;

public interface IDishServicePort {

    void createDish(Dish dish, String dishCategoryName);

}
