package com.pragma.plaza_service.infrastructure.out.jpa.adapter;

import com.pragma.plaza_service.domain.model.DishCategory;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.DishCategoryEntity;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IDishCategoryEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IDishCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishCategoryAdapterJPATest {

    @Mock
    private IDishCategoryRepository dishCategoryRepository;

    @Mock
    private IDishCategoryEntityMapper dishCategoryEntityMapper;

    @InjectMocks
    private DishCategoryAdapterJPA dishCategoryAdapterJPA;

    private final String CATEGORY_NAME = "Plato principal";
    private DishCategoryEntity dishCategoryEntity;
    private DishCategory dishCategory;

    @BeforeEach
    void setUp() {
        dishCategoryEntity = new DishCategoryEntity();
        dishCategoryEntity.setId(1L);
        dishCategoryEntity.setName(CATEGORY_NAME);
        dishCategoryEntity.setDescription("Platos principales del menú");

        dishCategory = new DishCategory();
        dishCategory.setId(1L);
        dishCategory.setName(CATEGORY_NAME);
        dishCategory.setDescription("Platos principales del menú");
    }

    @Test
    void findByName_WhenCategoryExists_ReturnsCategory() {
        // Arrange
        when(dishCategoryRepository.findByName(CATEGORY_NAME)).thenReturn(Optional.of(dishCategoryEntity));
        when(dishCategoryEntityMapper.toDishCategoryOptional(Optional.of(dishCategoryEntity))).thenReturn(Optional.of(dishCategory));

        // Act
        Optional<DishCategory> result = dishCategoryAdapterJPA.findByName(CATEGORY_NAME);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(CATEGORY_NAME, result.get().getName());
        verify(dishCategoryRepository, times(1)).findByName(CATEGORY_NAME);
        verify(dishCategoryEntityMapper, times(1)).toDishCategoryOptional(Optional.of(dishCategoryEntity));
    }

    @Test
    void findByName_WhenCategoryDoesNotExist_ReturnsEmptyOptional() {
        // Arrange
        when(dishCategoryRepository.findByName(CATEGORY_NAME)).thenReturn(Optional.empty());
        when(dishCategoryEntityMapper.toDishCategoryOptional(Optional.empty())).thenReturn(Optional.empty());

        // Act
        Optional<DishCategory> result = dishCategoryAdapterJPA.findByName(CATEGORY_NAME);

        // Assert
        assertFalse(result.isPresent());
        verify(dishCategoryRepository, times(1)).findByName(CATEGORY_NAME);
        verify(dishCategoryEntityMapper, times(1)).toDishCategoryOptional(Optional.empty());
    }

    @Test
    void findById_WithExistingId_ShouldReturnDishCategory() {
        // Arrange
        Long categoryId = 1L;

        when(dishCategoryRepository.findById(categoryId)).thenReturn(Optional.of(dishCategoryEntity));
        when(dishCategoryEntityMapper.toDishCategoryOptional(Optional.of(dishCategoryEntity)))
                .thenReturn(Optional.of(dishCategory));

        // Act
        Optional<DishCategory> result = dishCategoryAdapterJPA.findById(categoryId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(categoryId, result.get().getId());
        assertEquals(CATEGORY_NAME, result.get().getName());

        verify(dishCategoryRepository).findById(categoryId);
        verify(dishCategoryEntityMapper).toDishCategoryOptional(Optional.of(dishCategoryEntity));
    }

    @Test
    void findById_WithNonExistingId_ShouldReturnEmptyOptional() {
        // Arrange
        Long categoryId = 999L;

        when(dishCategoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        when(dishCategoryEntityMapper.toDishCategoryOptional(Optional.empty()))
                .thenReturn(Optional.empty());

        // Act
        Optional<DishCategory> result = dishCategoryAdapterJPA.findById(categoryId);

        // Assert
        assertFalse(result.isPresent());

        verify(dishCategoryRepository).findById(categoryId);
        verify(dishCategoryEntityMapper).toDishCategoryOptional(Optional.empty());
    }

}