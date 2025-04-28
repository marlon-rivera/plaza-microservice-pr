package com.pragma.plaza_service.domain.spi;

import com.pragma.plaza_service.domain.model.Dish;

import java.util.Optional;

public interface IDishPersistencePort {

    void createDish(Dish dish);
    void modifyDish(Dish dish);
    Optional<Dish> findById(Long id);

}
