package com.pragma.plaza_service.application.dto.utils.constants;

public class OrderDishRequestCreateDtoConstants {

    private OrderDishRequestCreateDtoConstants() {
        // Prevent instantiation
    }

    public static final String DISH_ID_DESCRIPTION = "ID of the dish to be ordered";
    public static final String DISH_ID_EXAMPLE = "1";
    public static final String DISH_ID_MUST_MANDATORY = "Dish ID must be mandatory";
    public static final String QUANTITY_DESCRIPTION = "Quantity of the dish to be ordered";
    public static final String QUANTITY_EXAMPLE = "2";
    public static final String QUANTITY_MUST_MANDATORY = "Quantity must be mandatory";
    public static final String QUANTITY_MUST_BE_GREATER_THAN_ZERO = "Quantity must be greater than zero";

}
