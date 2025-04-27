package com.pragma.plaza_service.domain.util.validators;

import com.pragma.plaza_service.domain.model.Restaurant;
import com.pragma.plaza_service.domain.util.constants.RestaurantValidatorConstants;
import com.pragma.plaza_service.domain.exception.InvalidDataException;

public class RestaurantValidator {

    private RestaurantValidator(){}

    public static void validateRestaurant(Restaurant restaurant){
        validateRequiredFields(restaurant);
        validateNit(restaurant.getNit());
        validatePhone(restaurant.getPhoneNumber());
        validateName(restaurant.getName());
    }

    private static void validateRequiredFields(Restaurant restaurant) {
        if (restaurant.getName() == null || restaurant.getName().isEmpty()) {
            throw new InvalidDataException(RestaurantValidatorConstants.EMPTY_NAME_MESSAGE);
        }
        if (restaurant.getNit() == null || restaurant.getNit().isEmpty()) {
            throw new InvalidDataException(RestaurantValidatorConstants.EMPTY_NIT_MESSAGE);
        }
        if (restaurant.getAddress() == null || restaurant.getAddress().isEmpty()) {
            throw new InvalidDataException(RestaurantValidatorConstants.EMPTY_ADDRESS_MESSAGE);
        }
        if (restaurant.getPhoneNumber() == null || restaurant.getPhoneNumber().isEmpty()) {
            throw new InvalidDataException(RestaurantValidatorConstants.EMPTY_PHONE_MESSAGE);
        }
        if (restaurant.getLogoUrl() == null || restaurant.getLogoUrl().isEmpty()) {
            throw new InvalidDataException(RestaurantValidatorConstants.EMPTY_LOGO_URL_MESSAGE);
        }
        if (restaurant.getOwnerId() == null) {
            throw new InvalidDataException(RestaurantValidatorConstants.EMPTY_OWNER_ID_MESSAGE);
        }
    }

    private static void validateNit(String nit) {
        if (!nit.matches(RestaurantValidatorConstants.ONLY_NUMBERS_REGEX)) {
            throw new InvalidDataException(RestaurantValidatorConstants.INVALID_NIT_FORMAT_MESSAGE);
        }
    }

    private static void validatePhone(String phone) {
        if (phone.length() > 13) {
            throw new InvalidDataException(RestaurantValidatorConstants.INVALID_PHONE_LENGTH_MESSAGE);
        }
        if (!phone.matches(RestaurantValidatorConstants.PHONE_REGEX)) {
            throw new InvalidDataException(RestaurantValidatorConstants.INVALID_PHONE_FORMAT_MESSAGE);
        }
    }

    private static void validateName(String name) {
        if (name.matches(RestaurantValidatorConstants.ONLY_NUMBERS_REGEX)) {
            throw new InvalidDataException(RestaurantValidatorConstants.INVALID_NAME_FORMAT_MESSAGE);
        }
    }
}
