package com.pragma.plaza_service.application.dto.request;

import com.pragma.plaza_service.application.dto.utils.constants.OrderListDtoConstants;
import com.pragma.plaza_service.domain.model.StatusOrderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderListDto {

    @Schema(
            description = OrderListDtoConstants.STATUS_DESCRIPTION,
            example = OrderListDtoConstants.STATUS_EXAMPLE
    )
    @NotNull(message = OrderListDtoConstants.STATUS_MUST_MANDATORY)
    private StatusOrderEnum status;
    @Schema(
            description = OrderListDtoConstants.SIZE_DESCRIPTION,
            example = OrderListDtoConstants.SIZE_EXAMPLE
    )
    @NotNull(message = OrderListDtoConstants.SIZE_MUST_MANDATORY)
    private Integer size;
    @Schema(
            description = OrderListDtoConstants.PAGE_DESCRIPTION,
            example = OrderListDtoConstants.PAGE_EXAMPLE
    )
    @NotNull(message = OrderListDtoConstants.PAGE_MUST_MANDATORY)
    private Integer page;

}
