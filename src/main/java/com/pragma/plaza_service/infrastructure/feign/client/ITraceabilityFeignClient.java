package com.pragma.plaza_service.infrastructure.feign.client;

import com.pragma.plaza_service.infrastructure.feign.request.SaveOrderLogDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "traceability-service", url = "${feign.client.config.traceability-service.url}")
public interface ITraceabilityFeignClient {

    @PostMapping("/order-log")
    void saveOrderLog(@RequestBody SaveOrderLogDto saveOrderLogDto);

}
