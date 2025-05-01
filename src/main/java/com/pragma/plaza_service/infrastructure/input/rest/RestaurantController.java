package com.pragma.plaza_service.infrastructure.input.rest;

import com.pragma.plaza_service.application.dto.request.RestaurantCreateDto;
import com.pragma.plaza_service.application.dto.response.PaginationInfoResponse;
import com.pragma.plaza_service.application.dto.response.RestaurantResponseDto;
import com.pragma.plaza_service.application.dto.utils.constants.ResponsesCodes;
import com.pragma.plaza_service.application.handler.IRestaurantHandler;
import com.pragma.plaza_service.infrastructure.util.constants.openapi.RestaurantControllerOpenApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final IRestaurantHandler restaurantHandler;

    @Operation(
            summary = RestaurantControllerOpenApiConstants.RESTAURANT_CREATE_SUMMARY,
            description = RestaurantControllerOpenApiConstants.RESTAURANT_CREATE_DESCRIPTION,
            responses = {
                    @ApiResponse(
                            responseCode = ResponsesCodes.CREATED,
                            description = RestaurantControllerOpenApiConstants.RESTAURANT_CREATE_RESPONSE_DESCRIPTION
                    ),
                    @ApiResponse(
                            responseCode = ResponsesCodes.BAD_REQUEST,
                            description = RestaurantControllerOpenApiConstants.RESTAURANT_CREATE_RESPONSE_400_DESCRIPTION
                    ),
                    @ApiResponse(
                            responseCode = ResponsesCodes.CONFLICT,
                            description = RestaurantControllerOpenApiConstants.RESTAURANT_CREATE_RESPONSE_409_DESCRIPTION
                    )
            }

    )
    @PostMapping
    public ResponseEntity<Void> createRestaurant(@Valid @RequestBody RestaurantCreateDto restaurantCreateDto) {
        restaurantHandler.createRestaurant(restaurantCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = RestaurantControllerOpenApiConstants.VALIDATE_RESTAURANT_OWNER_SUMMARY,
            description = RestaurantControllerOpenApiConstants.VALIDATE_RESTAURANT_OWNER_DESCRIPTION,
            responses = {
                    @ApiResponse(
                            responseCode = ResponsesCodes.OK,
                            description = RestaurantControllerOpenApiConstants.VALIDATE_RESTAURANT_OWNER_RESPONSE_DESCRIPTION
                    )
            }
    )
    @GetMapping("/validate-owner/{restaurantId}")
    public ResponseEntity<Boolean> validateOwnerRestaurant(@PathVariable Long restaurantId) {
        boolean isOwner = restaurantHandler.validateRestaurantOwner(restaurantId);
        return ResponseEntity.ok(isOwner);
    }

    @Operation(
            summary = RestaurantControllerOpenApiConstants.LIST_RESTAURANTS_SUMMARY,
            description = RestaurantControllerOpenApiConstants.LIST_RESTAURANTS_DESCRIPTION,
            responses = {
                    @ApiResponse(
                            responseCode = ResponsesCodes.OK,
                            description = RestaurantControllerOpenApiConstants.LIST_RESTAURANTS_RESPONSE_DESCRIPTION
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<PaginationInfoResponse<RestaurantResponseDto>> listRestaurants(
            @Parameter(
                    description = RestaurantControllerOpenApiConstants.DESCRIPTION_PARAM_PAGE,
                    example = RestaurantControllerOpenApiConstants.DEFAULT_PAGE
            )
            @RequestParam(defaultValue = RestaurantControllerOpenApiConstants.DEFAULT_PAGE) int page,
            @Parameter(
                    description = RestaurantControllerOpenApiConstants.DESCRIPTION_PARAM_SIZE,
                    example = RestaurantControllerOpenApiConstants.LIST_SIZE_DEFAULT
            )
            @Valid
            @Min(value = 1, message = RestaurantControllerOpenApiConstants.MIN_LIST_SIZE)
            @RequestParam(defaultValue = RestaurantControllerOpenApiConstants.LIST_SIZE_DEFAULT) int sizePage) {
        PaginationInfoResponse<RestaurantResponseDto> restaurants = restaurantHandler.listRestaurants(page, sizePage);
        return ResponseEntity.ok(restaurants);
    }
}
