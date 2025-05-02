package com.pragma.plaza_service.infrastructure.input.rest;

import com.pragma.plaza_service.application.dto.request.OrderListDto;
import com.pragma.plaza_service.application.dto.request.OrderRequestCreateDto;
import com.pragma.plaza_service.application.dto.response.OrderResponseDto;
import com.pragma.plaza_service.application.dto.response.PaginationInfoResponse;
import com.pragma.plaza_service.application.dto.utils.constants.ResponsesCodes;
import com.pragma.plaza_service.application.handler.IOrderHandler;
import com.pragma.plaza_service.infrastructure.util.constants.openapi.OrderControllerOpenApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(
            summary = OrderControllerOpenApiConstants.ORDER_CONTROLLER_GET_ALL_BY_STATUS_SUMMARY,
            description = OrderControllerOpenApiConstants.ORDER_CONTROLLER_GET_ALL_BY_STATUS_DESCRIPTION,
            responses = {
                    @ApiResponse(
                            responseCode = ResponsesCodes.OK,
                            description = OrderControllerOpenApiConstants.ORDER_CONTROLLER_GET_ALL_BY_STATUS_RESPONSE_200_DESCRIPTION
                    ),
                    @ApiResponse(
                            responseCode = ResponsesCodes.BAD_REQUEST,
                            description = OrderControllerOpenApiConstants.ORDER_CONTROLLER_GET_ALL_BY_STATUS_RESPONSE_400_DESCRIPTION
                    )
            }
    )
    @GetMapping
    public ResponseEntity<PaginationInfoResponse<OrderResponseDto>> getAllOrdersByStatus(@Valid @RequestBody OrderListDto orderListDto) {
        PaginationInfoResponse<OrderResponseDto> orders = orderHandler.getOrdersByStatus(orderListDto);
        return ResponseEntity.ok(orders);
    }

    @Operation(
            summary = OrderControllerOpenApiConstants.ORDER_CONTROLLER_ASSIGN_ORDER_SUMMARY,
            description = OrderControllerOpenApiConstants.ORDER_CONTROLLER_ASSIGN_ORDER_DESCRIPTION,
            responses = {
                    @ApiResponse(
                            responseCode = ResponsesCodes.OK,
                            description = OrderControllerOpenApiConstants.ORDER_CONTROLLER_ASSIGN_ORDER_RESPONSE_200_DESCRIPTION
                    ),
                    @ApiResponse(
                            responseCode = ResponsesCodes.BAD_REQUEST,
                            description = OrderControllerOpenApiConstants.ORDER_CONTROLLER_ASSIGN_ORDER_RESPONSE_400_DESCRIPTION
                    )
            }
    )
    @PutMapping("/assign/{orderId}")
    public ResponseEntity<Void> assignOrder(@PathVariable Long orderId) {
        orderHandler.assignOrder(orderId);
        return ResponseEntity.ok().build();
    }

}
