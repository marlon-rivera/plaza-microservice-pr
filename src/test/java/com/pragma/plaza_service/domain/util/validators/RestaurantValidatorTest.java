package com.pragma.plaza_service.domain.util.validators;

import com.pragma.plaza_service.domain.exception.InvalidDataException;
import com.pragma.plaza_service.domain.model.Restaurant;
import com.pragma.plaza_service.domain.util.constants.RestaurantValidatorConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantValidatorTest {

    @Test
    void validateRequiredFields_ValidRestaurant_ShouldNotThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("+573001234567")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        assertDoesNotThrow(() -> RestaurantValidator.validateRestaurant(restaurant));
    }

    @Test
    void validateRequiredFields_EmptyName_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("+573001234567")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.EMPTY_NAME_MESSAGE, exception.getMessage());
    }

    @Test
    void validateRequiredFields_NullName_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name(null)
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("+573001234567")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.EMPTY_NAME_MESSAGE, exception.getMessage());
    }

    @Test
    void validateRequiredFields_EmptyNit_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("")
                .address("Calle 123")
                .phoneNumber("+573001234567")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.EMPTY_NIT_MESSAGE, exception.getMessage());
    }

    @Test
    void validateRequiredFields_NullNit_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit(null)
                .address("Calle 123")
                .phoneNumber("+573001234567")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.EMPTY_NIT_MESSAGE, exception.getMessage());
    }

    @Test
    void validateRequiredFields_NullAddress_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("123456789")
                .address(null)
                .phoneNumber("+573001234567")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.EMPTY_ADDRESS_MESSAGE, exception.getMessage());
    }

    @Test
    void validateRequiredFields_EmptyAddress_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("123456789")
                .address("")
                .phoneNumber("+573001234567")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.EMPTY_ADDRESS_MESSAGE, exception.getMessage());
    }

    @Test
    void validateRequiredFields_NullPhone_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber(null)
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.EMPTY_PHONE_MESSAGE, exception.getMessage());
    }

    @Test
    void validateRequiredFields_EmptyPhone_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.EMPTY_PHONE_MESSAGE, exception.getMessage());
    }

    @Test
    void validateRequiredFields_NullLogoUrl_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("+573001234567")
                .logoUrl(null)
                .ownerId(1L)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.EMPTY_LOGO_URL_MESSAGE, exception.getMessage());
    }

    @Test
    void validateRequiredFields_EmptyLogoUrl_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("+573001234567")
                .logoUrl("")
                .ownerId(1L)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.EMPTY_LOGO_URL_MESSAGE, exception.getMessage());
    }

    @Test
    void validateRequiredFields_NullOwnerId_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("+573001234567")
                .logoUrl("http://example.com/logo.png")
                .ownerId(null)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.EMPTY_OWNER_ID_MESSAGE, exception.getMessage());
    }

    @Test
    void validateNit_ValidNit_ShouldNotThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("+573001234567")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        assertDoesNotThrow(() -> RestaurantValidator.validateRestaurant(restaurant));
    }

    @Test
    void validateNit_InvalidNit_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("ABC123")
                .address("Calle 123")
                .phoneNumber("+573001234567")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.INVALID_NIT_FORMAT_MESSAGE, exception.getMessage());
    }

    @Test
    void validatePhone_ValidPhone_ShouldNotThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("+573001234567")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        assertDoesNotThrow(() -> RestaurantValidator.validateRestaurant(restaurant));
    }

    @Test
    void validatePhone_TooLongPhone_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("+5730012345678901")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.INVALID_PHONE_LENGTH_MESSAGE, exception.getMessage());
    }

    @Test
    void validatePhone_InvalidFormatPhone_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("teléfono")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.INVALID_PHONE_FORMAT_MESSAGE, exception.getMessage());
    }

    @Test
    void validateName_ValidName_ShouldNotThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurante Prueba")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("+573001234567")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        assertDoesNotThrow(() -> RestaurantValidator.validateRestaurant(restaurant));
    }

    @Test
    void validateName_OnlyNumbersName_ShouldThrowException() {
        Restaurant restaurant = Restaurant.builder()
                .name("12345")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("+573001234567")
                .logoUrl("http://example.com/logo.png")
                .ownerId(1L)
                .build();

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> RestaurantValidator.validateRestaurant(restaurant));
        assertEquals(RestaurantValidatorConstants.INVALID_NAME_FORMAT_MESSAGE, exception.getMessage());
    }

}