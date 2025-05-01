package com.pragma.plaza_service.infrastructure.out.jpa.adapter;

import com.pragma.plaza_service.domain.model.Dish;
import com.pragma.plaza_service.domain.model.PaginationInfo;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishAdapterJPATest {

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IDishEntityMapper dishEntityMapper;

    @InjectMocks
    private DishAdapterJPA dishAdapterJPA;

    private Dish dish;
    private DishEntity dishEntity;
    private Long dishId = 1L;

    @BeforeEach
    void setUp() {
        // Configurar los objetos de prueba
        dish = new Dish();
        dishEntity = new DishEntity();
    }

    @Test
    void createDish_ShouldSaveDish() {
        // Arrange
        when(dishEntityMapper.toDishEntity(dish)).thenReturn(dishEntity);

        // Act
        dishAdapterJPA.createDish(dish);

        // Assert
        verify(dishEntityMapper).toDishEntity(dish);
        verify(dishRepository).save(dishEntity);
    }

    @Test
    void modifyDish_ShouldUpdateDish() {
        // Arrange
        when(dishEntityMapper.toDishEntity(dish)).thenReturn(dishEntity);

        // Act
        dishAdapterJPA.modifyDish(dish);

        // Assert
        verify(dishEntityMapper).toDishEntity(dish);
        verify(dishRepository).save(dishEntity);
    }

    @Test
    void findById_WhenDishExists_ShouldReturnDish() {
        // Arrange
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dishEntity));
        when(dishEntityMapper.toOptionalDish(Optional.of(dishEntity))).thenReturn(Optional.of(dish));

        // Act
        Optional<Dish> result = dishAdapterJPA.findById(dishId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(dish, result.get());
        verify(dishRepository).findById(dishId);
        verify(dishEntityMapper).toOptionalDish(Optional.of(dishEntity));
    }

    @Test
    void findById_WhenDishDoesNotExist_ShouldReturnEmptyOptional() {
        // Arrange
        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());
        when(dishEntityMapper.toOptionalDish(Optional.empty())).thenReturn(Optional.empty());

        // Act
        Optional<Dish> result = dishAdapterJPA.findById(dishId);

        // Assert
        assertFalse(result.isPresent());
        verify(dishRepository).findById(dishId);
        verify(dishEntityMapper).toOptionalDish(Optional.empty());
    }

    @Test
    void updateDish_ShouldUpdateDish() {
        // Arrange
        when(dishEntityMapper.toDishEntity(dish)).thenReturn(dishEntity);

        // Act
        dishAdapterJPA.updateDish(dish);

        // Assert
        verify(dishEntityMapper).toDishEntity(dish);
        verify(dishRepository).save(dishEntity);
    }

    @Test
    void listDishesByRestaurantAndCategory_ShouldReturnPaginatedDishes() {
        // Arrange
        Long restaurantId = 1L;
        Long categoryId = 2L;
        int page = 0;
        int sizePage = 10;

        DishEntity dish1 = new DishEntity();
        dish1.setName("Dish A");
        dish1.setPrice(BigDecimal.valueOf(10000L));

        DishEntity dish2 = new DishEntity();
        dish2.setName("Dish B");
        dish2.setPrice(BigDecimal.valueOf(15000L));

        List<DishEntity> dishEntities = List.of(dish1, dish2);
        Page<DishEntity> dishPage = new PageImpl<>(dishEntities, Pageable.ofSize(sizePage).withPage(page), 2);

        Dish dishModel1 = new Dish();
        dishModel1.setName("Dish A");
        dishModel1.setPrice(BigDecimal.valueOf(10000L));

        Dish dishModel2 = new Dish();
        dishModel2.setName("Dish B");
        dishModel2.setPrice(BigDecimal.valueOf(15000L));

        List<Dish> dishModels = List.of(dishModel1, dishModel2);

        when(dishRepository.findAllByRestaurantIdAndCategoryIdOrderByNameAsc(restaurantId, categoryId, Pageable.ofSize(sizePage).withPage(page)))
                .thenReturn(dishPage);
        when(dishEntityMapper.toDishList(dishEntities)).thenReturn(dishModels);

        // Act
        PaginationInfo<Dish> result = dishAdapterJPA.listDishesByRestaurantAndCategory(restaurantId, categoryId, page, sizePage);

        // Assert
        assertEquals(2, result.getList().size());
        assertEquals(page, result.getCurrentPage());
        assertEquals(sizePage, result.getPageSize());
        assertEquals(2L, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertFalse(result.isHasNextPage());
        assertFalse(result.isHasPreviousPage());

        verify(dishRepository).findAllByRestaurantIdAndCategoryIdOrderByNameAsc(restaurantId, categoryId, Pageable.ofSize(sizePage).withPage(page));
        verify(dishEntityMapper).toDishList(dishEntities);
    }

    @Test
    void listDishesByRestaurantAndCategory_WithEmptyResult_ShouldReturnEmptyPagination() {
        // Arrange
        Long restaurantId = 1L;
        Long categoryId = 2L;
        int page = 0;
        int sizePage = 10;

        List<DishEntity> emptyList = List.of();
        Page<DishEntity> emptyPage = new PageImpl<>(emptyList, Pageable.ofSize(sizePage).withPage(page), 0);

        when(dishRepository.findAllByRestaurantIdAndCategoryIdOrderByNameAsc(restaurantId, categoryId, Pageable.ofSize(sizePage).withPage(page)))
                .thenReturn(emptyPage);
        when(dishEntityMapper.toDishList(emptyList)).thenReturn(List.of());

        // Act
        PaginationInfo<Dish> result = dishAdapterJPA.listDishesByRestaurantAndCategory(restaurantId, categoryId, page, sizePage);

        // Assert
        assertTrue(result.getList().isEmpty());
        assertEquals(page, result.getCurrentPage());
        assertEquals(sizePage, result.getPageSize());
        assertEquals(0L, result.getTotalElements());
        assertEquals(0, result.getTotalPages());
        assertFalse(result.isHasNextPage());
        assertFalse(result.isHasPreviousPage());

        verify(dishRepository).findAllByRestaurantIdAndCategoryIdOrderByNameAsc(restaurantId, categoryId, Pageable.ofSize(sizePage).withPage(page));
        verify(dishEntityMapper).toDishList(emptyList);
    }
}