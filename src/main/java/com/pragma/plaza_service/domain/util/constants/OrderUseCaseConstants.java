package com.pragma.plaza_service.domain.util.constants;

public class OrderUseCaseConstants {

    private OrderUseCaseConstants() {
        // Prevent instantiation
    }

    public static final String ORDER_DISHES_CANNOT_BE_NULL_OR_EMPTY = "Order dishes cannot be null or empty";
    public static final String CLIENT_ID_CANNOT_BE_NULL = "Client ID cannot be null";
    public static final String RESTAURANT_ID_CANNOT_BE_NULL = "Restaurant ID cannot be null";
    public static final String ORDER_IN_PROGRESS = "There is already an order in progress for this client";
    public static final String RESTAURANT_NOT_FOUND = "Restaurant not found";
    public static final String DISH_NOT_FOUND = "Dish not found";
    public static final String EMPLOYEE_NOT_BELONG_TO_RESTAURANT = "Employee does not belong to the restaurant";
    public static final String ORDER_NOT_FOUND = "Order not found";
    public static final String ORDER_NOT_PENDING = "Order is not pending";
    public static final String ORDER_NOT_BELONG_TO_RESTAURANT = "Order does not belong to the restaurant";
    public static final String ORDER_NOT_IN_PROGRESS = "Order is not in progress";
    public static final String PHONE_NUMBER_NOT_FOUND = "Phone number not found";
    public static final String ORDER_NOT_READY = "Order is not ready";
    public static final String CODE_NOT_VALID = "Code is not valid";

}
