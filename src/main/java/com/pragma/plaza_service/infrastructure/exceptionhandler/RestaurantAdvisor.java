package com.pragma.plaza_service.infrastructure.exceptionhandler;

import com.pragma.plaza_service.domain.exception.UserNotOwnerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestaurantAdvisor {

    @ExceptionHandler(UserNotOwnerException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotOwnerException(UserNotOwnerException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ExceptionResponse(ex.getMessage(), HttpStatus.FORBIDDEN.toString(), LocalDateTime.now())
        );
    }

}
