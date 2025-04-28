package com.pragma.plaza_service.infrastructure.input.rest;

import com.pragma.plaza_service.application.dto.request.DishCreateDto;
import com.pragma.plaza_service.application.dto.request.DishEditDto;
import com.pragma.plaza_service.application.dto.utils.constants.ResponsesCodes;
import com.pragma.plaza_service.application.handler.IDishHandler;
import com.pragma.plaza_service.infrastructure.util.constants.openapi.DishControllerOpenApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DishController {

    private final IDishHandler dishHandler;

    @Operation(
            summary = DishControllerOpenApiConstants.DISH_CREATE_SUMMARY,
            description = DishControllerOpenApiConstants.DISH_CREATE_DESCRIPTION,
            responses = {
                    @ApiResponse(
                            responseCode = ResponsesCodes.CREATED,
                            description = DishControllerOpenApiConstants.DISH_CREATE_RESPONSE_DESCRIPTION
                    ),
                    @ApiResponse(
                            responseCode = ResponsesCodes.BAD_REQUEST,
                            description = DishControllerOpenApiConstants.DISH_CREATE_RESPONSE_400_DESCRIPTION
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Void> createDish(@Valid @RequestBody DishCreateDto dishCreateDto) {
        dishHandler.createDish(dishCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = DishControllerOpenApiConstants.DISH_EDIT_SUMMARY,
            description = DishControllerOpenApiConstants.DISH_EDIT_DESCRIPTION,
            responses = {
                    @ApiResponse(
                            responseCode = ResponsesCodes.OK,
                            description = DishControllerOpenApiConstants.DISH_EDIT_RESPONSE_DESCRIPTION
                    ),
                    @ApiResponse(
                            responseCode = ResponsesCodes.BAD_REQUEST,
                            description = DishControllerOpenApiConstants.DISH_EDIT_RESPONSE_400_DESCRIPTION
                    ),
                    @ApiResponse(
                            responseCode = ResponsesCodes.NOT_FOUND,
                            description = DishControllerOpenApiConstants.DISH_EDIT_RESPONSE_404_DESCRIPTION
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> editDish(@PathVariable Long id, @Valid @RequestBody DishEditDto dishEditDto) {
        dishHandler.modifyDish(dishEditDto, id);
        return ResponseEntity.ok().build();
    }



}
