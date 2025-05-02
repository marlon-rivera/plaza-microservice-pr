package com.pragma.plaza_service.application.dto.utils.constants;

public class OrderRequestCreateDtoConstants {

    private OrderRequestCreateDtoConstants(){
        // Prevent instantiation
    }

    public static final String RESTAURANT_ID_DESCRIPTION = "ID of the restaurant where the order is placed";
    public static final String RESTAURANT_ID_EXAMPLE = "1";
    public static final String RESTAURANT_ID_MUST_MANDATORY = "Restaurant ID must be mandatory";
    public static final String ORDER_DISHES_DESCRIPTION = "List of dishes in the order";
    public static final String ORDER_DISHES_EXAMPLE = "[{\"dishId\": 1, \"quantity\": 2}]";
    public static final String ORDER_DISHES_MUST_MANDATORY = "Order dishes must be mandatory";
    public static final String ORDER_DISHES_CANNOT_BE_NULL_OR_EMPTY = "Order dishes cannot be null or empty";

}
