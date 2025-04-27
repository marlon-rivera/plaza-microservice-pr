package com.pragma.plaza_service.application.dto.utils.constants;

public class RestaurantDtoConstants {

    private RestaurantDtoConstants(){}

    public static final String NAME_REQUIRED = "Restaurant name is required";
    public static final String NAME_REGEX = "^[a-zA-Z0-9\\s]+$";
    public static final String NAME_ONLY_NUMBERS = "Restaurant name cannot contain only numbers";

    public static final String NIT_REQUIRED = "Restaurant NIT is required";
    public static final String NIT_ONLY_NUMBERS = "NIT must contain only numeric characters";

    public static final String ADDRESS_REQUIRED = "Restaurant address is required";

    public static final String PHONE_REQUIRED = "Restaurant phone number is required";
    public static final String PHONE_FORMAT = "Phone number must follow the format: +573005698325, 573005698325 or 3005698325";
    public static final String PHONE_REGEX = "^(\\+\\d{12}|57\\d{10}|\\d{10})$";

    public static final String LOGO_URL_REQUIRED = "Restaurant logo URL is required";

    public static final String OWNER_ID_REQUIRED = "Owner ID is required";
    public static final String OWNER_INVALID = "The provided ID does not correspond to a user with owner role";

    public static final String ONLY_NUMBERS_REGEX = "^\\d+$";
    public static final String NAME_DESCRIPTION = "Restaurant name must be between 3 and 50 characters long and can only contain letters, numbers, and spaces.";
    public static final String NAME_EXAMPLE = "Restaurant Name";
    public static final String NIT_DESCRIPTION = "Restaurant NIT must be a numeric string.";
    public static final String NIT_EXAMPLE = "123456789";
    public static final String ADDRESS_DESCRIPTION = "Restaurant address must be between 3 and 100 characters long.";
    public static final String ADDRESS_EXAMPLE = "123 Main St, City, Country";
    public static final String PHONE_DESCRIPTION = "Phone number must be in the format: +573005698325, 573005698325 or 3005698325.";
    public static final String PHONE_EXAMPLE = "+573005698325";
    public static final String LOGO_URL_DESCRIPTION = "Logo URL must be a valid URL.";
    public static final String LOGO_URL_EXAMPLE = "https://example.com/logo.png";
    public static final String OWNER_ID_DESCRIPTION = "Owner ID must be a numeric string.";
    public static final String OWNER_ID_EXAMPLE = "123456789";

}
