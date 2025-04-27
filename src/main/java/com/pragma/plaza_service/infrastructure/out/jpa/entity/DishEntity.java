package com.pragma.plaza_service.infrastructure.out.jpa.entity;


import com.pragma.plaza_service.domain.model.DishCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "dish")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private DishCategoryEntity category;
    private Long restaurantId;
    private boolean isActive;

}
