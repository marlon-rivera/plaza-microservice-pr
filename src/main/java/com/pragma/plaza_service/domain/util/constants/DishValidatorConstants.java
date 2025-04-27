package com.pragma.plaza_service.domain.util.constants;

public class DishValidatorConstants {

    private DishValidatorConstants(){}

    public static final String NAME_REQUIRED = "Dish name is required";
    public static final String DESCRIPTION_REQUIRED = "Dish description is required";
    public static final String IMAGE_URL_REQUIRED = "Dish image URL is required";
    public static final String CATEGORY_REQUIRED = "Dish category is required";
    public static final String INVALID_PRICE = "Price must be a positive integer greater than zero";
    public static final String RESTAURANT_REQUIRED = "Dish must be associated with a restaurant";

}
