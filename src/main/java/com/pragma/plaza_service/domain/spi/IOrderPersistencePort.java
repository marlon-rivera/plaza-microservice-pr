package com.pragma.plaza_service.domain.spi;

import com.pragma.plaza_service.domain.model.Order;

public interface IOrderPersistencePort {

    void saveOrder(Order order);
    boolean existsOrderInProgressByClientId(Long clientId);

}
