package com.pragma.plaza_service.infrastructure.feign.adapter;

import com.pragma.plaza_service.infrastructure.feign.client.IUserFeignClient;
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

}