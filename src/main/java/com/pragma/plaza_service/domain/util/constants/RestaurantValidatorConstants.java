package com.pragma.plaza_service.domain.util.constants;

public class RestaurantValidatorConstants {

    private RestaurantValidatorConstants(){}

    public static final String EMPTY_NAME_MESSAGE = "Restaurant name is required";
    public static final String EMPTY_NIT_MESSAGE = "Restaurant NIT is required";
    public static final String EMPTY_ADDRESS_MESSAGE = "Restaurant address is required";
    public static final String EMPTY_PHONE_MESSAGE = "Restaurant phone is required";
    public static final String EMPTY_LOGO_URL_MESSAGE = "Restaurant logo URL is required";
    public static final String EMPTY_OWNER_ID_MESSAGE = "Owner ID is required";
    public static final String INVALID_OWNER_ROLE_MESSAGE = "The provided ID does not correspond to a user with owner role";
    public static final String INVALID_NIT_FORMAT_MESSAGE = "NIT must contain only numeric characters";
    public static final String INVALID_PHONE_FORMAT_MESSAGE = "Phone must follow the format +573005698325, 3005698325 or start with 57";
    public static final String INVALID_PHONE_LENGTH_MESSAGE = "Phone must not exceed 13 characters";
    public static final String INVALID_NAME_FORMAT_MESSAGE = "Restaurant name cannot contain only numbers";
    public static final String PHONE_REGEX = "^(\\+\\d{12}|57\\d{10}|\\d{10})$";
    public static final String ONLY_NUMBERS_REGEX = "^\\d+$";

}
