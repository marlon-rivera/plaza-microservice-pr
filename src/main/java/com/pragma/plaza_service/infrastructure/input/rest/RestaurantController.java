package com.pragma.plaza_service.infrastructure.input.rest;

import com.pragma.plaza_service.application.dto.request.RestaurantCreateDto;
import com.pragma.plaza_service.application.dto.utils.constants.ResponsesCodes;
import com.pragma.plaza_service.application.handler.IRestaurantHandler;
import com.pragma.plaza_service.infrastructure.util.constants.openapi.RestaurantControllerOpenApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
