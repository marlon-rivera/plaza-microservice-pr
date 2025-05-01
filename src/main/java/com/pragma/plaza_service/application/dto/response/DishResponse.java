package com.pragma.plaza_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class DishResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;

}
