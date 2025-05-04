package com.pragma.plaza_service.infrastructure.feign.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendConfirmationDto {

    private Long idOrder;
    private String phoneNumber;

}
