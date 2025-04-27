package com.pragma.plaza_service.application.dto.request;

import com.pragma.plaza_service.application.dto.utils.constants.RestaurantDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestaurantCreateDto {

    @Schema(
            description = RestaurantDtoConstants.NAME_DESCRIPTION,
            example = RestaurantDtoConstants.NAME_EXAMPLE
    )
    @NotBlank(message = RestaurantDtoConstants.NAME_REQUIRED)
    @Pattern(regexp = RestaurantDtoConstants.NAME_REGEX,
            message = RestaurantDtoConstants.NAME_ONLY_NUMBERS)
    private String name;
    @Schema(
            description = RestaurantDtoConstants.NIT_DESCRIPTION,
            example = RestaurantDtoConstants.NIT_EXAMPLE
    )
    @NotBlank(message = RestaurantDtoConstants.NIT_REQUIRED)
    @Pattern(regexp = RestaurantDtoConstants.ONLY_NUMBERS_REGEX,
            message = RestaurantDtoConstants.NIT_ONLY_NUMBERS)
    private String nit;
    @Schema(
            description = RestaurantDtoConstants.ADDRESS_DESCRIPTION,
            example = RestaurantDtoConstants.ADDRESS_EXAMPLE
    )
    @NotBlank(message = RestaurantDtoConstants.ADDRESS_REQUIRED)
    private String address;
    @Schema(
            description = RestaurantDtoConstants.PHONE_DESCRIPTION,
            example = RestaurantDtoConstants.PHONE_EXAMPLE
    )
    @NotBlank(message = RestaurantDtoConstants.PHONE_REQUIRED)
    @Pattern(regexp = RestaurantDtoConstants.PHONE_REGEX,
            message = RestaurantDtoConstants.PHONE_FORMAT)
    private String phoneNumber;
    @Schema(
            description = RestaurantDtoConstants.LOGO_URL_DESCRIPTION,
            example = RestaurantDtoConstants.LOGO_URL_EXAMPLE
    )
    @NotBlank(message = RestaurantDtoConstants.LOGO_URL_REQUIRED)
    private String logoUrl;
    @Schema(
            description = RestaurantDtoConstants.OWNER_ID_DESCRIPTION,
            example = RestaurantDtoConstants.OWNER_ID_EXAMPLE
    )
    @NotNull(message = RestaurantDtoConstants.OWNER_ID_REQUIRED)
    private Long ownerId;
}
