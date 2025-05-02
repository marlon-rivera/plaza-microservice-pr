package com.pragma.plaza_service.application.dto.response;

import com.pragma.plaza_service.domain.model.OrderDish;
import com.pragma.plaza_service.domain.model.Restaurant;
import com.pragma.plaza_service.domain.model.StatusOrderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponseDto {

    private Long id;
    private Long clientId;
    private Restaurant restaurant;
    private StatusOrderEnum status;
    private LocalDateTime createdAt;
    private List<OrderDish> orderDishes;

}
