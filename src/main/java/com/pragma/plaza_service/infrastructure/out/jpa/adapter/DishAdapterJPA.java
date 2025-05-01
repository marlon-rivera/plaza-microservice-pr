package com.pragma.plaza_service.infrastructure.out.jpa.adapter;

import com.pragma.plaza_service.domain.model.Dish;
import com.pragma.plaza_service.domain.model.PaginationInfo;
import com.pragma.plaza_service.domain.spi.IDishPersistencePort;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @Override
    public PaginationInfo<Dish> listDishesByRestaurantAndCategory(Long restaurantId, Long categoryId, int page, int sizePage) {
        Pageable pageable = Pageable.ofSize(sizePage).withPage(page);
        Page<DishEntity> dishEntityPage = dishRepository.findAllByRestaurantIdAndCategoryIdOrderByNameAsc(restaurantId, categoryId, pageable);
        return new PaginationInfo<>(
                dishEntityMapper.toDishList(dishEntityPage.getContent()),
                dishEntityPage.getNumber(),
                dishEntityPage.getSize(),
                dishEntityPage.getTotalElements(),
                dishEntityPage.getTotalPages(),
                dishEntityPage.hasNext(),
                dishEntityPage.hasPrevious()
        );
    }
}
