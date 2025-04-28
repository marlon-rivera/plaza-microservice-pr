package com.pragma.plaza_service.infrastructure.out.jpa.adapter;

import com.pragma.plaza_service.domain.model.Restaurant;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantAdapterJPATest {

    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;

    @Mock
    private IRestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantAdapterJPA restaurantAdapterJPA;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveRestaurant_ShouldSaveRestaurantEntity() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Restaurante Prueba");
        restaurant.setNit("123456789");

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setName("Restaurante Prueba");
        restaurantEntity.setNit("123456789");

        when(restaurantEntityMapper.toRestaurantEntity(restaurant)).thenReturn(restaurantEntity);

        restaurantAdapterJPA.saveRestaurant(restaurant);

        verify(restaurantEntityMapper, times(1)).toRestaurantEntity(restaurant);
        verify(restaurantRepository, times(1)).save(restaurantEntity);
    }

    @Test
    void existsByNit_ShouldReturnTrue_WhenRestaurantExists() {
        String nit = "123456789";
        when(restaurantRepository.existsByNit(nit)).thenReturn(true);

        boolean result = restaurantAdapterJPA.existsByNit(nit);

        assertTrue(result);
        verify(restaurantRepository, times(1)).existsByNit(nit);
    }

    @Test
    void existsByNit_ShouldReturnFalse_WhenRestaurantDoesNotExist() {
        String nit = "987654321";
        when(restaurantRepository.existsByNit(nit)).thenReturn(false);

        boolean result = restaurantAdapterJPA.existsByNit(nit);

        assertFalse(result);
        verify(restaurantRepository, times(1)).existsByNit(nit);
    }

    @Test
    void findOwnerIdByRestaurantId_ShouldReturnOwnerId_WhenRestaurantExists() {
        Long restaurantId = 1L;
        Long expectedOwnerId = 2L;
        when(restaurantRepository.findOwnerIdById(restaurantId)).thenReturn(expectedOwnerId);

        Long result = restaurantAdapterJPA.findOwnerIdByRestaurantId(restaurantId);

        assertEquals(expectedOwnerId, result);
        verify(restaurantRepository, times(1)).findOwnerIdById(restaurantId);
    }

}