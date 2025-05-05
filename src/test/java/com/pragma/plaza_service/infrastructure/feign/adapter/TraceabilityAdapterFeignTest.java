package com.pragma.plaza_service.infrastructure.feign.adapter;

import com.pragma.plaza_service.infrastructure.feign.client.ITraceabilityFeignClient;
import com.pragma.plaza_service.infrastructure.feign.request.SaveOrderLogDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TraceabilityAdapterFeignTest {


    @Mock
    private ITraceabilityFeignClient traceabilityFeignClient;

    @InjectMocks
    private TraceabilityAdapterFeign traceabilityAdapterFeign;

    @Test
    void saveTraceability_ShouldCallFeignClient() {
        // Arrange
        Long orderId = 1L;
        Long clientId = 2L;
        Long restaurantId = 3L;
        Long employeeId = 4L;
        String status = "DELIVERED";

        // Act
        traceabilityAdapterFeign.saveTraceability(orderId, clientId, restaurantId, employeeId, status);

        // Assert
        verify(traceabilityFeignClient).saveOrderLog(
                new SaveOrderLogDto(orderId, clientId, restaurantId, employeeId, status)
        );
    }

}