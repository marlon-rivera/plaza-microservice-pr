package com.pragma.plaza_service.application.dto.request;

import com.pragma.plaza_service.application.dto.utils.constants.OrderDishRequestCreateDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDishRequestCreateDto {

    @Schema(
            description = OrderDishRequestCreateDtoConstants.DISH_ID_DESCRIPTION,
            example = OrderDishRequestCreateDtoConstants.DISH_ID_EXAMPLE
    )
    @NotNull(message = OrderDishRequestCreateDtoConstants. DISH_ID_MUST_MANDATORY)
    private Long dishId;
    @Schema(
            description = OrderDishRequestCreateDtoConstants.QUANTITY_DESCRIPTION,
            example = OrderDishRequestCreateDtoConstants.QUANTITY_EXAMPLE
    )
    @NotNull(message = OrderDishRequestCreateDtoConstants.QUANTITY_MUST_MANDATORY)
    @Min(value = 1, message = OrderDishRequestCreateDtoConstants.QUANTITY_MUST_BE_GREATER_THAN_ZERO)
    private Integer quantity;

}
