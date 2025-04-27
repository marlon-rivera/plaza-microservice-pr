package com.pragma.plaza_service.infrastructure.out.jpa.repository;

import com.pragma.plaza_service.infrastructure.out.jpa.entity.DishCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDishCategoryRepository extends JpaRepository<DishCategoryEntity, Long> {
    Optional<DishCategoryEntity> findByName(String name);
}
