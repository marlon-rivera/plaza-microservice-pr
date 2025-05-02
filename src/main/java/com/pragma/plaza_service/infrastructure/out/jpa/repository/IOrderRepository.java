package com.pragma.plaza_service.infrastructure.out.jpa.repository;

import com.pragma.plaza_service.infrastructure.out.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM OrderEntity o WHERE o.clientId = :clientId AND o.status NOT IN ('DELIVERED', 'CANCELLED')")
    boolean existsOrderInProgressByClientId(Long clientId);

}