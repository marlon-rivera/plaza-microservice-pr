package com.pragma.plaza_service.domain.exception;

import com.pragma.plaza_service.domain.util.constants.RestaurantUseCaseConstants;

public class UserNotOwnerException extends RuntimeException {
    public UserNotOwnerException() {
        super(RestaurantUseCaseConstants.USER_NOT_OWNER);
    }
}
