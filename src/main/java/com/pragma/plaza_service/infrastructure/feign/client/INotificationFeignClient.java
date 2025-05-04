package com.pragma.plaza_service.infrastructure.feign.client;

import com.pragma.plaza_service.infrastructure.feign.request.SendConfirmationDto;
import com.pragma.plaza_service.infrastructure.feign.request.ValidateConfirmationCodeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "${feign.notification.url}")
public interface INotificationFeignClient {

    @PostMapping("/notification")
    void sendNotification(@RequestBody SendConfirmationDto sendConfirmationDto);

    @PostMapping("/notification/validate")
    boolean validateConfirmationCode(@RequestBody ValidateConfirmationCodeDto validateConfirmationCodeDto);

}
