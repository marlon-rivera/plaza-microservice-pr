package com.pragma.plaza_service.infrastructure.out.jpa.repository;

import com.pragma.plaza_service.infrastructure.out.jpa.entity.OrderDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDishRepository extends JpaRepository<OrderDishEntity, Long> {
}
