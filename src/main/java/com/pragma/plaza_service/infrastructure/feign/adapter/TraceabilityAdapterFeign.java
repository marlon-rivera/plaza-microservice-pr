package com.pragma.plaza_service.infrastructure.feign.adapter;

import com.pragma.plaza_service.domain.spi.ITraceabilityPersistencePort;
import com.pragma.plaza_service.infrastructure.feign.client.ITraceabilityFeignClient;
import com.pragma.plaza_service.infrastructure.feign.request.SaveOrderLogDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TraceabilityAdapterFeign implements ITraceabilityPersistencePort {

    private final ITraceabilityFeignClient traceabilityFeignClient;

    @Override
    public void saveTraceability(Long idOrder, Long idClient, Long idRestaurant, Long idEmployee, String status) {
        traceabilityFeignClient.saveOrderLog(
                new SaveOrderLogDto(idOrder, idClient, idRestaurant, idEmployee, status)
        );
    }
}
