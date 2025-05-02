package com.pragma.plaza_service.infrastructure.out.jpa.adapter;

import com.pragma.plaza_service.domain.model.Order;
import com.pragma.plaza_service.domain.model.OrderDish;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IOrderDishRepository;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderAdapterJPATest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IOrderEntityMapper orderEntityMapper;

    @Mock
    private IOrderDishRepository orderDishRepository;

    @InjectMocks
    private OrderAdapterJPA orderAdapterJPA;

@Test
void saveOrder_ShouldSaveOrderAndOrderDishes() {
    // Arrange
    Order order = new Order();
    List<OrderDish> orderDishes = new ArrayList<>();
    OrderDish orderDish1 = new OrderDish();
    OrderDish orderDish2 = new OrderDish();
    orderDishes.add(orderDish1);
    orderDishes.add(orderDish2);
    order.setOrderDishes(orderDishes);

    OrderEntity orderEntity = new OrderEntity();
    OrderDishEntity orderDishEntity1 = new OrderDishEntity();
    OrderDishEntity orderDishEntity2 = new OrderDishEntity();
    List<OrderDishEntity> orderDishEntities = List.of(orderDishEntity1, orderDishEntity2);

    when(orderEntityMapper.toEntity(order)).thenReturn(orderEntity);
    when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
    when(orderEntityMapper.toOrderDishEntity(orderDish1)).thenReturn(orderDishEntity1);
    when(orderEntityMapper.toOrderDishEntity(orderDish2)).thenReturn(orderDishEntity2);

    // Capturar los argumentos pasados a saveAll para verificar después
    ArgumentCaptor<List<OrderDishEntity>> orderDishCaptor = ArgumentCaptor.forClass(List.class);

    // Act
    orderAdapterJPA.saveOrder(order);

    // Assert
    verify(orderRepository).save(orderEntity);
    verify(orderDishRepository).saveAll(orderDishCaptor.capture());
    verify(orderEntityMapper, times(2)).toOrderDishEntity(any(OrderDish.class));

    // Verificamos que cada OrderDishEntity tenga la referencia correcta
    List<OrderDishEntity> capturedOrderDishes = orderDishCaptor.getValue();
    assertNotNull(capturedOrderDishes);
    assertFalse(capturedOrderDishes.isEmpty());

    for (OrderDishEntity entity : capturedOrderDishes) {
        assertEquals(orderEntity, entity.getOrderEntity());
    }
}

    @Test
    void existsOrderInProgressByClientId_ShouldReturnTrue_WhenOrderExists() {
        // Arrange
        Long clientId = 1L;
        when(orderRepository.existsOrderInProgressByClientId(clientId)).thenReturn(true);

        // Act
        boolean result = orderAdapterJPA.existsOrderInProgressByClientId(clientId);

        // Assert
        assertTrue(result);
        verify(orderRepository).existsOrderInProgressByClientId(clientId);
    }

    @Test
    void existsOrderInProgressByClientId_ShouldReturnFalse_WhenOrderDoesNotExist() {
        // Arrange
        Long clientId = 1L;
        when(orderRepository.existsOrderInProgressByClientId(clientId)).thenReturn(false);

        // Act
        boolean result = orderAdapterJPA.existsOrderInProgressByClientId(clientId);

        // Assert
        assertFalse(result);
        verify(orderRepository).existsOrderInProgressByClientId(clientId);
    }

}