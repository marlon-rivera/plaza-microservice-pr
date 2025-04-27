package com.pragma.plaza_service.infrastructure.util.constants.openapi;

public class RestaurantControllerOpenApiConstants {

    public RestaurantControllerOpenApiConstants(){}

    public static final String RESTAURANT_TAG = "Restaurant";
    public static final String RESTAURANT_CREATE_SUMMARY = "Create a restaurant";
    public static final String RESTAURANT_CREATE_DESCRIPTION = "This endpoint allows you to create a new restaurant.";
    public static final String RESTAURANT_CREATE_RESPONSE_DESCRIPTION = "Restaurant created successfully.";
    public static final String RESTAURANT_CREATE_RESPONSE_400_DESCRIPTION = "Invalid request data.";
    public static final String RESTAURANT_CREATE_RESPONSE_409_DESCRIPTION = "Restaurant already exists with the Nit provided.";

}
