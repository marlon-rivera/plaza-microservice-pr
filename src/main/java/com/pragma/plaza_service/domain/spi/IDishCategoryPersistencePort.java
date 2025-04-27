package com.pragma.plaza_service.domain.spi;

import com.pragma.plaza_service.domain.model.DishCategory;

import java.util.Optional;

public interface IDishCategoryPersistencePort {

    Optional<DishCategory> findByName(String name);

}
