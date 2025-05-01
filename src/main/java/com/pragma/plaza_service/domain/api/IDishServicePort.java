package com.pragma.plaza_service.domain.api;

import com.pragma.plaza_service.domain.model.Dish;
import com.pragma.plaza_service.domain.model.PaginationInfo;

import java.math.BigDecimal;

public interface IDishServicePort {

    void createDish(Dish dish, String dishCategoryName);
    void modifyDish(Long id, String description, BigDecimal price);
    void changeDishStatus(Long id, boolean status);
    PaginationInfo<Dish> listDishes( Long restaurantId, Long categoryId, int page, int sizePage);
}
