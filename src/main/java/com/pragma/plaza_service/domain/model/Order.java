package com.pragma.plaza_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Order {

    private Long id;
    private Long clientId;
    private Restaurant restaurant;
    private StatusOrderEnum status;
    private LocalDateTime createdAt;
    private List<OrderDish> orderDishes;
}
