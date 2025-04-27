package com.pragma.plaza_service.infrastructure.configuration;

import com.pragma.plaza_service.application.handler.IRestaurantHandler;
import com.pragma.plaza_service.application.handler.impl.RestaurantHandler;
import com.pragma.plaza_service.application.mapper.IRestaurantRequestMapper;
import com.pragma.plaza_service.domain.api.IRestaurantServicePort;
import com.pragma.plaza_service.domain.spi.IRestaurantPersistencePort;
import com.pragma.plaza_service.domain.spi.IUserPersistencePort;
import com.pragma.plaza_service.domain.usecase.RestaurantUseCase;
import com.pragma.plaza_service.infrastructure.feign.adapter.UserAdapterFeign;
import com.pragma.plaza_service.infrastructure.feign.client.IUserFeignClient;
import com.pragma.plaza_service.infrastructure.out.jpa.adapter.RestaurantAdapterJPA;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IUserFeignClient userFeignClient;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantAdapterJPA(restaurantEntityMapper, restaurantRepository);
    }

    @Bean
    public IUserPersistencePort userPersistencePort(){
        return new UserAdapterFeign(userFeignClient);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(){
        return new RestaurantUseCase(restaurantPersistencePort(), userPersistencePort());
    }

    @Bean
    public IRestaurantHandler restaurantHandler(){
        return new RestaurantHandler(restaurantRequestMapper, restaurantServicePort());
    }
}
