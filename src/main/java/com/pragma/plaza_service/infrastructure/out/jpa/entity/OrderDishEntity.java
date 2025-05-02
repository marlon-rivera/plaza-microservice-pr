package com.pragma.plaza_service.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_dish")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orderEntity;
    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private DishEntity dishEntity;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}
