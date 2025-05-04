package com.pragma.plaza_service.infrastructure.feign.adapter;

import com.pragma.plaza_service.domain.spi.IUserPersistencePort;
import com.pragma.plaza_service.infrastructure.feign.client.IUserFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAdapterFeign implements IUserPersistencePort {

    private final IUserFeignClient userFeignClient;

    @Override
    public boolean isOwner(Long ownerId) {
        return userFeignClient.isOwner(ownerId);
    }

    @Override
    public Long getIdRestaurantByIdEmployee() {
        try{
            return userFeignClient.getIdRestaurantByIdEmployee();
        }catch (FeignException e){
            return null;
        }
    }

    @Override
    public String getPhoneNumberByIdClient(Long idClient){
        try{
            return userFeignClient.getPhoneNumberByIdClient(idClient);
        }catch (FeignException e){
            return null;
        }
    }
}
