package com.pragma.plaza_service.application.dto.request;

import com.pragma.plaza_service.application.dto.utils.constants.DishUpdateStatusDtoConstants;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DishUpdateStatusDto {

    @NotNull(
            message = DishUpdateStatusDtoConstants.DISH_STATUS_MUST_MANDATORY
    )
    private Boolean status;

}
