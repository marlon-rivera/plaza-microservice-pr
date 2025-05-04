package com.pragma.plaza_service.domain.spi;

public interface IUserPersistencePort {

    boolean isOwner(Long ownerId);
    Long getIdRestaurantByIdEmployee();
    String getPhoneNumberByIdClient(Long idClient);
}
