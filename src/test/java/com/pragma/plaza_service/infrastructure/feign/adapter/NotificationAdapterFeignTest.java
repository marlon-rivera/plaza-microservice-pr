package com.pragma.plaza_service.infrastructure.feign.adapter;

import com.pragma.plaza_service.infrastructure.feign.client.INotificationFeignClient;
import com.pragma.plaza_service.infrastructure.feign.request.SendConfirmationDto;
import com.pragma.plaza_service.infrastructure.feign.request.ValidateConfirmationCodeDto;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationAdapterFeignTest {

    @Mock
    private INotificationFeignClient notificationFeignClient;

    private NotificationAdapterFeign notificationAdapterFeign;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationAdapterFeign = new NotificationAdapterFeign(notificationFeignClient);
    }

    @Test
    void sendNotification_shouldCallFeignClient_whenDataIsValid() {
        // Arrange
        Long orderId = 1L;
        String phoneNumber = "+1234567890";
        doNothing().when(notificationFeignClient).sendNotification(any(SendConfirmationDto.class));

        // Act
        notificationAdapterFeign.sendNotification(orderId, phoneNumber);

        // Assert
        verify(notificationFeignClient, times(1)).sendNotification(
                argThat(dto ->
                        dto.getIdOrder().equals(orderId) &&
                                dto.getPhoneNumber().equals(phoneNumber)
                )
        );
    }

    @Test
    void sendNotification_shouldHandleFeignException_whenClientFails() {
        // Arrange
        Long orderId = 1L;
        String phoneNumber = "+1234567890";
        doThrow(FeignException.class).when(notificationFeignClient).sendNotification(any(SendConfirmationDto.class));

        // Act & Assert
        assertThrows(FeignException.class, () ->
                notificationAdapterFeign.sendNotification(orderId, phoneNumber)
        );
    }

    @Test
    void validateConfirmationCode_ShouldReturnTrueWhenValidationSucceeds() {
        // Arrange
        Long orderId = 1L;
        String code = "123456";
        ValidateConfirmationCodeDto dto = new ValidateConfirmationCodeDto(orderId, code);
        when(notificationFeignClient.validateConfirmationCode(dto)).thenReturn(true);

        // Act
        boolean result = notificationAdapterFeign.validateConfirmationCode(orderId, code);

        // Assert
        assertTrue(result);
        verify(notificationFeignClient, times(1)).validateConfirmationCode(dto);
    }

    @Test
    void validateConfirmationCode_ShouldReturnFalseWhenFeignExceptionOccurs() {
        // Arrange
        Long orderId = 1L;
        String code = "123456";
        ValidateConfirmationCodeDto dto = new ValidateConfirmationCodeDto(orderId, code);
        when(notificationFeignClient.validateConfirmationCode(dto)).thenThrow(FeignException.class);

        // Act
        boolean result = notificationAdapterFeign.validateConfirmationCode(orderId, code);

        // Assert
        assertFalse(result);
        verify(notificationFeignClient, times(1)).validateConfirmationCode(dto);
    }

}