package com.pragma.plaza_service.infrastructure.out.jpa.adapter;

import com.pragma.plaza_service.domain.model.Order;
import com.pragma.plaza_service.domain.model.OrderDish;
import com.pragma.plaza_service.domain.model.PaginationInfo;
import com.pragma.plaza_service.domain.model.StatusOrderEnum;
import com.pragma.plaza_service.domain.spi.ITraceabilityPersistencePort;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Mock
    private ITraceabilityPersistencePort traceabilityPersistencePort;

    @InjectMocks
    private OrderAdapterJPA orderAdapterJPA;

    @Test
    void saveOrder_ShouldSaveOrderAndOrderDishes() {
        // Arrange
        Order order = new Order();
        // Configuramos los datos necesarios para traceability
        order.setId(1L);
        order.setClientId(2L);
        com.pragma.plaza_service.domain.model.Restaurant restaurant = new com.pragma.plaza_service.domain.model.Restaurant();
        restaurant.setId(3L);
        order.setRestaurant(restaurant);
        order.setStatus(com.pragma.plaza_service.domain.model.StatusOrderEnum.PENDING);
        order.setIdEmployee(null);

        List<OrderDish> orderDishes = new ArrayList<>();
        OrderDish orderDish1 = new OrderDish();
        OrderDish orderDish2 = new OrderDish();
        orderDishes.add(orderDish1);
        orderDishes.add(orderDish2);
        order.setOrderDishes(orderDishes);

        OrderEntity orderEntity = new OrderEntity();
        // Configuramos el ID para que sea devuelto en el mock
        orderEntity.setId(1L);
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

        // Verificamos la llamada a traceabilityPersistencePort
        verify(traceabilityPersistencePort).saveTraceability(
                orderEntity.getId(),
                order.getClientId(),
                order.getRestaurant().getId(),
                order.getIdEmployee(),
                order.getStatus().name()
        );

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

    @Test
    void getOrdersByIdRestaurantAndStatus_ShouldReturnPaginatedResult() {
        // Arrange
        Long restaurantId = 1L;
        String status = "PENDING";
        int page = 0;
        int size = 10;

        List<OrderEntity> orderEntities = List.of(new OrderEntity(), new OrderEntity());
        List<Order> orders = List.of(new Order(), new Order());

        Page<OrderEntity> pageResult = mock(Page.class);

        when(orderRepository.findAllByRestaurantEntityIdAndStatus(restaurantId, status,
                Pageable.ofSize(size).withPage(page))).thenReturn(pageResult);
        when(pageResult.getContent()).thenReturn(orderEntities);
        when(orderEntityMapper.toDomainList(orderEntities)).thenReturn(orders);
        when(pageResult.getNumber()).thenReturn(page);
        when(pageResult.getSize()).thenReturn(size);
        when(pageResult.getTotalElements()).thenReturn(20L);
        when(pageResult.getTotalPages()).thenReturn(2);
        when(pageResult.hasNext()).thenReturn(true);
        when(pageResult.hasPrevious()).thenReturn(false);

        // Act
        PaginationInfo<Order> result = orderAdapterJPA.getOrdersByIdRestaurantAndStatus(
                restaurantId, status, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(orders, result.getList());
        assertEquals(page, result.getCurrentPage());
        assertEquals(size, result.getPageSize());
        assertEquals(20L, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
        assertTrue(result.isHasNextPage());
        assertFalse(result.isHasPreviousPage());

        verify(orderRepository).findAllByRestaurantEntityIdAndStatus(restaurantId, status,
                Pageable.ofSize(size).withPage(page));
        verify(orderEntityMapper).toDomainList(orderEntities);
    }

    @Test
    void findById_ShouldReturnOrder_WhenOrderExists() {
        // Arrange
        Long orderId = 1L;
        OrderEntity orderEntity = new OrderEntity();
        Order order = new Order();
        Optional<OrderEntity> optionalOrderEntity = Optional.of(orderEntity);
        Optional<Order> optionalOrder = Optional.of(order);

        when(orderRepository.findById(orderId)).thenReturn(optionalOrderEntity);
        when(orderEntityMapper.toOptionalDomain(optionalOrderEntity)).thenReturn(optionalOrder);

        // Act
        Optional<Order> result = orderAdapterJPA.findById(orderId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(order, result.get());
        verify(orderRepository).findById(orderId);
        verify(orderEntityMapper).toOptionalDomain(optionalOrderEntity);
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenOrderDoesNotExist() {
        // Arrange
        Long orderId = 1L;
        Optional<OrderEntity> optionalOrderEntity = Optional.empty();
        Optional<Order> optionalOrder = Optional.empty();

        when(orderRepository.findById(orderId)).thenReturn(optionalOrderEntity);
        when(orderEntityMapper.toOptionalDomain(optionalOrderEntity)).thenReturn(optionalOrder);

        // Act
        Optional<Order> result = orderAdapterJPA.findById(orderId);

        // Assert
        assertFalse(result.isPresent());
        verify(orderRepository).findById(orderId);
        verify(orderEntityMapper).toOptionalDomain(optionalOrderEntity);
    }

    @Test
    void updateOrder_ShouldUpdateExistingOrder() {
        // Arrange
        Order order = new Order();
        order.setId(1L);
        order.setClientId(2L);
        order.setRestaurant(new com.pragma.plaza_service.domain.model.Restaurant());
        order.getRestaurant().setId(3L);
        order.setStatus(com.pragma.plaza_service.domain.model.StatusOrderEnum.DELIVERED);
        order.setIdEmployee(4L);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setClientId(2L);
        orderEntity.setRestaurantEntity(new com.pragma.plaza_service.infrastructure.out.jpa.entity.RestaurantEntity());
        orderEntity.getRestaurantEntity().setId(3L);
        orderEntity.setStatus(StatusOrderEnum.DELIVERED.name());

        when(orderEntityMapper.toEntity(order)).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        // Act
        orderAdapterJPA.updateOrder(order);

        // Assert
        verify(orderEntityMapper).toEntity(order);
        verify(orderRepository).save(orderEntity);
        verify(traceabilityPersistencePort).saveTraceability(
                orderEntity.getId(),
                orderEntity.getClientId(),
                orderEntity.getRestaurantEntity().getId(),
                order.getIdEmployee(),
                orderEntity.getStatus()
        );
    }

}