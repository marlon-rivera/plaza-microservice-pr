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

}