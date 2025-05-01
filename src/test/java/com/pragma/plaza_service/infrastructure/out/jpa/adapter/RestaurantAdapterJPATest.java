package com.pragma.plaza_service.infrastructure.out.jpa.adapter;

import com.pragma.plaza_service.domain.model.PaginationInfo;
import com.pragma.plaza_service.domain.model.Restaurant;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    @Test
    void listRestaurants_ShouldReturnPaginatedRestaurants() {
        // Arrange
        int page = 0;
        int sizePage = 10;

        Pageable pageable = Pageable.ofSize(sizePage).withPage(page);

        RestaurantEntity restaurant1 = new RestaurantEntity();
        restaurant1.setName("Restaurante A");
        restaurant1.setNit("123456789");

        RestaurantEntity restaurant2 = new RestaurantEntity();
        restaurant2.setName("Restaurante B");
        restaurant2.setNit("987654321");

        List<RestaurantEntity> restaurantEntities = List.of(restaurant1, restaurant2);

        Page<RestaurantEntity> restaurantPage = new PageImpl<>(restaurantEntities, pageable, 2);

        Restaurant restaurantModel1 = new Restaurant();
        restaurantModel1.setName("Restaurante A");
        restaurantModel1.setNit("123456789");

        Restaurant restaurantModel2 = new Restaurant();
        restaurantModel2.setName("Restaurante B");
        restaurantModel2.setNit("987654321");

        List<Restaurant> restaurantModels = List.of(restaurantModel1, restaurantModel2);

        when(restaurantRepository.findAllByOrderByNameAsc(any(Pageable.class))).thenReturn(restaurantPage);
        when(restaurantEntityMapper.toRestaurantList(restaurantEntities)).thenReturn(restaurantModels);

        // Act
        PaginationInfo<Restaurant> result = restaurantAdapterJPA.listRestaurants(page, sizePage);

        // Assert
        assertEquals(2, result.getList().size());
        assertEquals(page, result.getCurrentPage());
        assertEquals(sizePage, result.getPageSize());
        assertEquals(2L, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertFalse(result.isHasNextPage());
        assertFalse(result.isHasPreviousPage());

        verify(restaurantRepository).findAllByOrderByNameAsc(any(Pageable.class));
        verify(restaurantEntityMapper).toRestaurantList(restaurantEntities);
    }

    @Test
    void listRestaurants_WithEmptyResult_ShouldReturnEmptyPagination() {
        // Arrange
        int page = 0;
        int sizePage = 10;

        Pageable pageable = PageRequest.of(page, sizePage);
        List<RestaurantEntity> emptyList = List.of();
        Page<RestaurantEntity> emptyPage = new PageImpl<>(emptyList, pageable, 0);

        when(restaurantRepository.findAllByOrderByNameAsc(any(Pageable.class))).thenReturn(emptyPage);
        when(restaurantEntityMapper.toRestaurantList(emptyList)).thenReturn(List.of());

        // Act
        PaginationInfo<Restaurant> result = restaurantAdapterJPA.listRestaurants(page, sizePage);

        // Assert
        assertTrue(result.getList().isEmpty());
        assertEquals(page, result.getCurrentPage());
        assertEquals(sizePage, result.getPageSize());
        assertEquals(0L, result.getTotalElements());
        assertEquals(0, result.getTotalPages());
        assertFalse(result.isHasNextPage());
        assertFalse(result.isHasPreviousPage());

        verify(restaurantRepository).findAllByOrderByNameAsc(any(Pageable.class));
        verify(restaurantEntityMapper).toRestaurantList(emptyList);
    }

}