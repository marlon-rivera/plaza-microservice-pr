package com.pragma.plaza_service.infrastructure.out.jpa.adapter;

import com.pragma.plaza_service.domain.model.Dish;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}