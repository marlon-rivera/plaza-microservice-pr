package com.pragma.plaza_service.infrastructure.feign.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SaveOrderLogDto {

    private Long idOrder;
    private Long idClient;
    private Long idRestaurant;
    private Long idEmployee;
    private String status;

}
