package com.pragma.plaza_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RestaurantResponseDto {

    private Long id;
    private String name;
    private String logoUrl;

}
