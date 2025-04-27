package com.pragma.plaza_service.application.dto.request;

import com.pragma.plaza_service.application.dto.utils.constants.DishCreateDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DishCreateDto {

    @Schema(
            description = DishCreateDtoConstants.NAME_DESCRIPTION,
            example = DishCreateDtoConstants.NAME_EXAMPLE
    )
    @NotBlank(message = DishCreateDtoConstants.NAME_REQUIRED)
    private String name;
    @Schema(
            description = DishCreateDtoConstants.DESCRIPTION_DESCRIPTION,
            example = DishCreateDtoConstants.DESCRIPTION_EXAMPLE
    )
    @NotBlank(message = DishCreateDtoConstants.DESCRIPTION_REQUIRED)
    private String description;
    @Schema(
            description = DishCreateDtoConstants.IMAGE_URL_DESCRIPTION,
            example = DishCreateDtoConstants.IMAGE_URL_EXAMPLE
    )
    @NotBlank(message = DishCreateDtoConstants.IMAGE_URL_REQUIRED)
    private String imageUrl;
    @Schema(
            description = DishCreateDtoConstants.CATEGORY_DESCRIPTION,
            example = DishCreateDtoConstants.CATEGORY_EXAMPLE
    )
    @NotBlank(message = DishCreateDtoConstants.CATEGORY_REQUIRED)
    private String category;
    @Schema(
            description = DishCreateDtoConstants.DISH_PRICE_DESCRIPTION,
            example = DishCreateDtoConstants.DISH_PRICE_EXAMPLE
    )
    @NotNull(message = DishCreateDtoConstants.DISH_PRICE_REQUIRED)
    @DecimalMin(value = DishCreateDtoConstants.MIN_PRICE, message = DishCreateDtoConstants.INVALID_PRICE)
    private BigDecimal price;
    @Schema(
            description = DishCreateDtoConstants.RESTAURANT_DESCRIPTION,
            example = DishCreateDtoConstants.RESTAURANT_EXAMPLE
    )
    @NotNull(message = DishCreateDtoConstants.RESTAURANT_REQUIRED)
    private Long restaurantId;

}
