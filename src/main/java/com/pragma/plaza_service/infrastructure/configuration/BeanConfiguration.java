package com.pragma.plaza_service.infrastructure.configuration;

import com.pragma.plaza_service.application.handler.IDishHandler;
import com.pragma.plaza_service.application.handler.IRestaurantHandler;
import com.pragma.plaza_service.application.handler.impl.DishHandler;
import com.pragma.plaza_service.application.handler.impl.RestaurantHandler;
import com.pragma.plaza_service.application.mapper.IDishRequestMapper;
import com.pragma.plaza_service.application.mapper.IRestaurantRequestMapper;
import com.pragma.plaza_service.domain.api.IDishServicePort;
import com.pragma.plaza_service.domain.api.IRestaurantServicePort;
import com.pragma.plaza_service.domain.spi.*;
import com.pragma.plaza_service.domain.usecase.DishUseCase;
import com.pragma.plaza_service.domain.usecase.RestaurantUseCase;
import com.pragma.plaza_service.infrastructure.authenticate.AuthenticateAdapter;
import com.pragma.plaza_service.infrastructure.feign.adapter.UserAdapterFeign;
import com.pragma.plaza_service.infrastructure.feign.client.IUserFeignClient;
import com.pragma.plaza_service.infrastructure.out.jpa.adapter.DishAdapterJPA;
import com.pragma.plaza_service.infrastructure.out.jpa.adapter.DishCategoryAdapterJPA;
import com.pragma.plaza_service.infrastructure.out.jpa.adapter.RestaurantAdapterJPA;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IDishCategoryEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IDishCategoryRepository;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IDishRepository;
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
    private final IDishCategoryRepository dishCategoryRepository;
    private final IDishRepository dishRepository;
    private final IDishRequestMapper dishRequestMapper;
    private final IDishEntityMapper dishEntityMapper;
    private final IDishCategoryEntityMapper dishCategoryEntityMapper;

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
        return new RestaurantUseCase(restaurantPersistencePort(), userPersistencePort(), autthenticatePort());
    }

    @Bean
    public IRestaurantHandler restaurantHandler(){
        return new RestaurantHandler(restaurantRequestMapper, restaurantServicePort());
    }

    @Bean
    public IDishPersistencePort dishPersistencePort(){
        return new DishAdapterJPA(dishRepository, dishEntityMapper);
    }

    @Bean
    public IDishCategoryPersistencePort dishCategoryPersistencePort(){
        return new DishCategoryAdapterJPA(dishCategoryRepository, dishCategoryEntityMapper);
    }

    @Bean
    public IAutthenticatePort autthenticatePort(){
        return new AuthenticateAdapter();
    }

    @Bean
    public IDishServicePort dishServicePort(){
        return new DishUseCase(dishPersistencePort(), dishCategoryPersistencePort(), restaurantPersistencePort(), autthenticatePort());
    }

    @Bean
    public IDishHandler dishHandler(){
        return new DishHandler(dishServicePort(), dishRequestMapper);
    }
}
