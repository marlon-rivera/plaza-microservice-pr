package com.pragma.plaza_service.infrastructure.feign.adapter;

import com.pragma.plaza_service.domain.spi.INotificationPersistencePort;
import com.pragma.plaza_service.infrastructure.feign.client.INotificationFeignClient;
import com.pragma.plaza_service.infrastructure.feign.request.SendConfirmationDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationAdapterFeign implements INotificationPersistencePort {

    private final INotificationFeignClient notificationFeignClient;

    @Override
    public void sendNotification(Long idOrder, String phoneNumber) {
        notificationFeignClient.sendNotification(
                new SendConfirmationDto(idOrder, phoneNumber)
        );
    }
}
