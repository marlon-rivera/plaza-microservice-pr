package com.pragma.plaza_service.application.dto.request;

import com.pragma.plaza_service.application.dto.utils.constants.OrderRequestCreateDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequestCreateDto {

    @Schema(
            description = OrderRequestCreateDtoConstants.RESTAURANT_ID_DESCRIPTION,
            example = OrderRequestCreateDtoConstants.RESTAURANT_ID_EXAMPLE
    )
    @NotNull(message = OrderRequestCreateDtoConstants.RESTAURANT_ID_MUST_MANDATORY)
    private Long restaurantId;
    @Schema(
            description = OrderRequestCreateDtoConstants.ORDER_DISHES_DESCRIPTION,
            example = OrderRequestCreateDtoConstants.ORDER_DISHES_EXAMPLE
    )
    @NotNull(message = OrderRequestCreateDtoConstants.ORDER_DISHES_MUST_MANDATORY)
    @NotEmpty(message = OrderRequestCreateDtoConstants.ORDER_DISHES_CANNOT_BE_NULL_OR_EMPTY)
    @Valid
    private List<OrderDishRequestCreateDto> orderDishes;

}
