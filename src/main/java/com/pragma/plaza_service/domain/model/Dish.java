package com.pragma.plaza_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Dish {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private DishCategory category;
    private Long restaurantId;
    private boolean isActive;
}
