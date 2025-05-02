package com.pragma.plaza_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDish {

    private Long id;
    private Order order;
    private Dish dish;
    private Integer quantity;

}
