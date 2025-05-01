package com.pragma.plaza_service.infrastructure.util.constants.openapi;

public class RestaurantControllerOpenApiConstants {

    private RestaurantControllerOpenApiConstants(){
        // Prevent instantiation
    }

    public static final String RESTAURANT_CREATE_SUMMARY = "Create a restaurant";
    public static final String RESTAURANT_CREATE_DESCRIPTION = "This endpoint allows you to create a new restaurant.";
    public static final String RESTAURANT_CREATE_RESPONSE_DESCRIPTION = "Restaurant created successfully.";
    public static final String RESTAURANT_CREATE_RESPONSE_400_DESCRIPTION = "Invalid request data.";
    public static final String RESTAURANT_CREATE_RESPONSE_409_DESCRIPTION = "Restaurant already exists with the Nit provided.";
    public static final String VALIDATE_RESTAURANT_OWNER_SUMMARY = "Validate restaurant owner";
    public static final String VALIDATE_RESTAURANT_OWNER_DESCRIPTION = "This endpoint allows you to validate if the user is the owner of the restaurant.";
    public static final String VALIDATE_RESTAURANT_OWNER_RESPONSE_DESCRIPTION = "Validation successful.";

}
