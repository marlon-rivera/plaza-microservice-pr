package com.pragma.plaza_service.infrastructure.feign.adapter;

import com.pragma.plaza_service.domain.spi.INotificationPersistencePort;
import com.pragma.plaza_service.infrastructure.feign.client.INotificationFeignClient;
import com.pragma.plaza_service.infrastructure.feign.request.SendConfirmationDto;
import com.pragma.plaza_service.infrastructure.feign.request.ValidateConfirmationCodeDto;
import feign.FeignException;
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

    @Override
    public boolean validateConfirmationCode(Long idOrder, String code) {
        try{
            return notificationFeignClient.validateConfirmationCode(
                    new ValidateConfirmationCodeDto(idOrder, code)
            );
        }catch (FeignException e){
            return false;
        }
    }

    @Override
    public boolean sendNotificationCancelOrder(String phoneNumber) {
        try {
            notificationFeignClient.sendNotificationCancelOrder(phoneNumber);
            return true;
        } catch (FeignException e) {
            return false;
        }
    }
}
