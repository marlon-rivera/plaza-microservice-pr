package com.pragma.plaza_service.infrastructure.out.jpa.repository;

import com.pragma.plaza_service.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {

    Page<DishEntity> findAllByRestaurantIdAndCategoryIdOrderByNameAsc(Long restaurantId, Long categoryId, Pageable pageable);

}
