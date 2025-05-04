package com.pragma.plaza_service.infrastructure.feign.adapter;

import com.pragma.plaza_service.infrastructure.feign.client.IUserFeignClient;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAdapterFeignTest {

    @Mock
    private IUserFeignClient userFeignClient;

    private UserAdapterFeign userAdapterFeign;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userAdapterFeign = new UserAdapterFeign(userFeignClient);
    }

    @Test
    void isOwner_shouldReturnTrue_whenUserIsOwner() {
        Long ownerId = 1L;
        when(userFeignClient.isOwner(ownerId)).thenReturn(true);

        boolean result = userAdapterFeign.isOwner(ownerId);

        assertTrue(result);
        verify(userFeignClient).isOwner(ownerId);
    }

    @Test
    void isOwner_shouldReturnFalse_whenUserIsNotOwner() {
        Long ownerId = 2L;
        when(userFeignClient.isOwner(ownerId)).thenReturn(false);

        boolean result = userAdapterFeign.isOwner(ownerId);

        assertFalse(result);
        verify(userFeignClient).isOwner(ownerId);
    }

    @Test
    void getIdRestaurantByIdEmployee_shouldReturnId_whenSuccessful() {
        Long expectedId = 5L;
        when(userFeignClient.getIdRestaurantByIdEmployee()).thenReturn(expectedId);

        Long result = userAdapterFeign.getIdRestaurantByIdEmployee();

        assertEquals(expectedId, result);
        verify(userFeignClient).getIdRestaurantByIdEmployee();
    }

    @Test
    void getIdRestaurantByIdEmployee_shouldReturnNull_whenFeignExceptionOccurs() {
        when(userFeignClient.getIdRestaurantByIdEmployee()).thenThrow(FeignException.class);

        Long result = userAdapterFeign.getIdRestaurantByIdEmployee();

        assertNull(result);
        verify(userFeignClient).getIdRestaurantByIdEmployee();
    }

    @Test
    void getPhoneNumberByIdClient_shouldReturnPhoneNumber_whenSuccessful() {
        Long clientId = 3L;
        String expectedPhoneNumber = "+1234567890";
        when(userFeignClient.getPhoneNumberByIdClient(clientId)).thenReturn(expectedPhoneNumber);

        String result = userAdapterFeign.getPhoneNumberByIdClient(clientId);

        assertEquals(expectedPhoneNumber, result);
        verify(userFeignClient).getPhoneNumberByIdClient(clientId);
    }

    @Test
    void getPhoneNumberByIdClient_shouldReturnNull_whenFeignExceptionOccurs() {
        Long clientId = 4L;
        when(userFeignClient.getPhoneNumberByIdClient(clientId)).thenThrow(FeignException.class);

        String result = userAdapterFeign.getPhoneNumberByIdClient(clientId);

        assertNull(result);
        verify(userFeignClient).getPhoneNumberByIdClient(clientId);
    }

}