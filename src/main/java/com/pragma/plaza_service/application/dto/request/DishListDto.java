package com.pragma.plaza_service.application.dto.request;

import com.pragma.plaza_service.application.dto.utils.constants.DishListDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DishListDto {

    @Schema(
            description = DishListDtoConstants.DISH_LIST_ID_RESTAURANT_DESCRIPTION,
            example = DishListDtoConstants.DISH_LIST_ID_RESTAURANT_EXAMPLE
    )
    @NotNull(message = DishListDtoConstants.DISH_LIST_ID_RESTAURANT_MUST_MANDATORY)
    private Long restaurantId;
    @Schema(
            description = DishListDtoConstants.DISH_LIST_ID_CATEGORY_DESCRIPTION,
            example = DishListDtoConstants.DISH_LIST_ID_CATEGORY_EXAMPLE
    )
    @NotNull(message = DishListDtoConstants.DISH_LIST_ID_CATEGORY_MUST_MANDATORY)
    private Long categoryId;
    @Schema(
            description = DishListDtoConstants.DISH_LIST_SIZE_DESCRIPTION,
            example = DishListDtoConstants.DISH_LIST_SIZE_EXAMPLE
    )
    @NotNull(message = DishListDtoConstants.DISH_LIST_SIZE_MUST_MANDATORY)
    private Integer pageSize;
    @Schema(
            description = DishListDtoConstants.DISH_LIST_PAGE_DESCRIPTION,
            example = DishListDtoConstants.DISH_LIST_PAGE_EXAMPLE
    )
    @NotNull(message = DishListDtoConstants.DISH_LIST_PAGE_MUST_MANDATORY)
    private Integer pageNumber;

}
