package com.pragma.plaza_service.application.dto.request;

import com.pragma.plaza_service.application.dto.utils.constants.DeliverOrderDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliverOrderDto {

    @Schema(
            description = DeliverOrderDtoConstants.ID_ORDER_DESCRIPTION,
            example = DeliverOrderDtoConstants.ID_ORDER_EXAMPLE
    )
    @NotNull(message = DeliverOrderDtoConstants.ID_ORDER_NULL_MESSAGE)
    private Long idOrder;
    @Schema(
            description = DeliverOrderDtoConstants.CODE_DESCRIPTION,
            example = DeliverOrderDtoConstants.CODE_EXAMPLE
    )
    @NotNull(message = DeliverOrderDtoConstants.CODE_NULL_MESSAGE)
    private String code;

}
