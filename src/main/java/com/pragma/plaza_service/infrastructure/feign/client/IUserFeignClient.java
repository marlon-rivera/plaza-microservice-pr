package com.pragma.plaza_service.infrastructure.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${feign.client.config.user-service.url}")
public interface IUserFeignClient {

    @GetMapping("/users/owner/{id}")
    boolean isOwner(@PathVariable("id")  Long id);
    @GetMapping("/users/restaurant/employee")
    Long getIdRestaurantByIdEmployee();
    @GetMapping("/users/client-phone/{id}")
    String getPhoneNumberByIdClient(@PathVariable("id") Long idClient);
}
