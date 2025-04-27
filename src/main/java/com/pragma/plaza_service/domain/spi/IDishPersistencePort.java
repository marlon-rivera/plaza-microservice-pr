package com.pragma.plaza_service.domain.spi;

import com.pragma.plaza_service.domain.model.Dish;

public interface IDishPersistencePort {

    void createDish(Dish dish);

}
