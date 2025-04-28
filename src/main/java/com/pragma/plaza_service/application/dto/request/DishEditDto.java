package com.pragma.plaza_service.application.dto.request;

import com.pragma.plaza_service.application.dto.utils.constants.DishEditDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DishEditDto {

    @Schema(
            description = DishEditDtoConstants.DESCRIPTION_DESCRIPTION,
            example = DishEditDtoConstants.DESCRIPTION_EXAMPLE
    )
    private String description;
    @Schema(
            description = DishEditDtoConstants.PRICE_DESCRIPTION,
            example = DishEditDtoConstants.PRICE_EXAMPLE
    )
    @DecimalMin(value = DishEditDtoConstants.MIN_PRICE, message = DishEditDtoConstants.PRICE_MUST_BE_POSITIVE)
    private BigDecimal price;

}
