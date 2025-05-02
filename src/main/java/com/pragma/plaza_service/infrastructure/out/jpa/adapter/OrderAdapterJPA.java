package com.pragma.plaza_service.infrastructure.out.jpa.adapter;

import com.pragma.plaza_service.domain.model.Order;
import com.pragma.plaza_service.domain.model.OrderDish;
import com.pragma.plaza_service.domain.spi.IOrderPersistencePort;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IOrderDishRepository;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OrderAdapterJPA implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderDishRepository orderDishRepository;

    @Override
    public void saveOrder(Order order) {
        OrderEntity orderEntity = orderRepository.save(orderEntityMapper.toEntity(order));
        List<OrderDishEntity> orderDishEntities = new ArrayList<>();
        for (OrderDish orderDish : order.getOrderDishes()) {
            OrderDishEntity orderDishEntity = orderEntityMapper.toOrderDishEntity(orderDish);
            orderDishEntity.setOrderEntity(orderEntity);
            orderDishEntities.add(orderDishEntity);
        }
        orderDishRepository.saveAll(orderDishEntities);
    }

    @Override
    public boolean existsOrderInProgressByClientId(Long clientId) {
        return orderRepository.existsOrderInProgressByClientId(clientId);
    }
}
