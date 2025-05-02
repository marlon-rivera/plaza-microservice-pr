package com.pragma.plaza_service.infrastructure.util.constants.openapi;

public class OrderControllerOpenApiConstants {

    private OrderControllerOpenApiConstants(){
        // Prevent instantiation
    }

    public static final String ORDER_CONTROLLER_CREATE_SUMMARY = "Create a new order";
    public static final String ORDER_CONTROLLER_CREATE_DESCRIPTION = "Create a new order for a client.";
    public static final String ORDER_CONTROLLER_CREATE_RESPONSE_201_DESCRIPTION = "Order created successfully.";
    public static final String ORDER_CONTROLLER_CREATE_RESPONSE_400_DESCRIPTION = "Bad request. Invalid input.";
    public static final String ORDER_CONTROLLER_CREATE_RESPONSE_409_DESCRIPTION = "Conflict. Order already in progress.";
    public static final String ORDER_CONTROLLER_GET_ALL_BY_STATUS_SUMMARY = "Get all orders by status";
    public static final String ORDER_CONTROLLER_GET_ALL_BY_STATUS_DESCRIPTION = "Get all orders by status.";
    public static final String ORDER_CONTROLLER_GET_ALL_BY_STATUS_RESPONSE_200_DESCRIPTION = "Orders retrieved successfully.";
    public static final String ORDER_CONTROLLER_GET_ALL_BY_STATUS_RESPONSE_400_DESCRIPTION = "Bad request. Invalid input.";

}
