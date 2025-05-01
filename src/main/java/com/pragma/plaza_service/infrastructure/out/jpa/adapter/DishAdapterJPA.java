package com.pragma.plaza_service.infrastructure.out.jpa.adapter;

import com.pragma.plaza_service.domain.model.Dish;
import com.pragma.plaza_service.domain.spi.IDishPersistencePort;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DishAdapterJPA implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public void createDish(Dish dish) {
        dishRepository.save(
                dishEntityMapper.toDishEntity(dish)
        );
    }

    @Override
    public void modifyDish(Dish dish) {
        dishRepository.save(
                dishEntityMapper.toDishEntity(dish)
        );
    }

    @Override
    public Optional<Dish> findById(Long id) {
        return dishEntityMapper.toOptionalDish(
                dishRepository.findById(id)
        );
    }

    @Override
    public void updateDish(Dish dish) {
        dishRepository.save(
                dishEntityMapper.toDishEntity(dish)
        );
    }
}
