package com.pragma.plaza_service.application.dto.utils.constants;

public class DishCreateDtoConstants {

    private DishCreateDtoConstants(){}

    public static final String NAME_REQUIRED = "Dish name is required";
    public static final String NAME_EXAMPLE = "Dish name example";
    public static final String NAME_DESCRIPTION = "The name of the dish";
    public static final String DESCRIPTION_REQUIRED = "Dish description is required";
    public static final String DESCRIPTION_EXAMPLE = "Dish description example";
    public static final String DESCRIPTION_DESCRIPTION = "The description of the dish";
    public static final String IMAGE_URL_REQUIRED = "Dish image URL is required";
    public static final String IMAGE_URL_EXAMPLE = "https://example.com/dish-image.jpg";
    public static final String IMAGE_URL_DESCRIPTION = "The URL of the dish image";
    public static final String CATEGORY_REQUIRED = "Dish category is required";
    public static final String CATEGORY_EXAMPLE = "Dish category example";
    public static final String CATEGORY_DESCRIPTION = "The category of the dish";
    public static final String DISH_PRICE_REQUIRED = "Dish price is required";
    public static final String DISH_PRICE_EXAMPLE = "10000";
    public static final String DISH_PRICE_DESCRIPTION = "The price of the dish";
    public static final String INVALID_PRICE = "Price must be a positive integer greater than zero";
    public static final String RESTAURANT_REQUIRED = "Dish must be associated with a restaurant";
    public static final String RESTAURANT_EXAMPLE = "1";
    public static final String RESTAURANT_DESCRIPTION = "The ID of the restaurant to which the dish belongs";
    public static final String MIN_PRICE = "0.01";

}
