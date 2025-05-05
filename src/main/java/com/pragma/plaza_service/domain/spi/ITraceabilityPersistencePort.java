package com.pragma.plaza_service.domain.spi;

public interface ITraceabilityPersistencePort {

    void saveTraceability(Long idOrder, Long idClient, Long idRestaurant, Long idEmployee, String status);

}
