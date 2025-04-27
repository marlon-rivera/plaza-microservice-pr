package com.pragma.plaza_service.infrastructure.out.jpa.adapter;

import com.pragma.plaza_service.domain.model.DishCategory;
import com.pragma.plaza_service.domain.spi.IDishCategoryPersistencePort;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IDishCategoryEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IDishCategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DishCategoryAdapterJPA implements IDishCategoryPersistencePort {

    private final IDishCategoryRepository dishCategoryRepository;
    private final IDishCategoryEntityMapper dishCategoryEntityMapper;

    @Override
    public Optional<DishCategory> findByName(String name) {
        return dishCategoryEntityMapper.toDishCategoryOptional(
                dishCategoryRepository.findByName(name)
        );
    }
}
