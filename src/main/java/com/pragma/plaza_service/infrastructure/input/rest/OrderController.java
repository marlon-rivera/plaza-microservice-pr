package com.pragma.plaza_service.infrastructure.input.rest;

import com.pragma.plaza_service.application.dto.request.OrderRequestCreateDto;
import com.pragma.plaza_service.application.dto.utils.constants.ResponsesCodes;
import com.pragma.plaza_service.application.handler.IOrderHandler;
import com.pragma.plaza_service.infrastructure.util.constants.openapi.OrderControllerOpenApiConstants;
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
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderHandler orderHandler;

    @Operation(
            summary = OrderControllerOpenApiConstants.ORDER_CONTROLLER_CREATE_SUMMARY,
            description = OrderControllerOpenApiConstants.ORDER_CONTROLLER_CREATE_DESCRIPTION,
            responses = {
                    @ApiResponse(
                            responseCode = ResponsesCodes.CREATED,
                            description = OrderControllerOpenApiConstants.ORDER_CONTROLLER_CREATE_RESPONSE_201_DESCRIPTION
                    ),
                    @ApiResponse(
                            responseCode = ResponsesCodes.BAD_REQUEST,
                            description = OrderControllerOpenApiConstants.ORDER_CONTROLLER_CREATE_RESPONSE_400_DESCRIPTION
                    ),
                    @ApiResponse(
                            responseCode = ResponsesCodes.CONFLICT,
                            description = OrderControllerOpenApiConstants.ORDER_CONTROLLER_CREATE_RESPONSE_409_DESCRIPTION
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<Void> createOrder(@Valid @RequestBody OrderRequestCreateDto orderRequestCreateDto) {
        orderHandler.createOrder(orderRequestCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
