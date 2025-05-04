package com.pragma.plaza_service.domain.spi;

public interface INotificationPersistencePort {

    void sendNotification(Long idOrder, String phoneNumber);

}
