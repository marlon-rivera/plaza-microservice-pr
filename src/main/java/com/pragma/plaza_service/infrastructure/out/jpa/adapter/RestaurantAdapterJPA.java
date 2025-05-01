package com.pragma.plaza_service.infrastructure.out.jpa.adapter;

import com.pragma.plaza_service.domain.model.PaginationInfo;
import com.pragma.plaza_service.domain.model.Restaurant;
import com.pragma.plaza_service.domain.spi.IRestaurantPersistencePort;
import com.pragma.plaza_service.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.plaza_service.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plaza_service.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class RestaurantAdapterJPA implements IRestaurantPersistencePort {

    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IRestaurantRepository restaurantRepository;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurantEntityMapper.toRestaurantEntity(restaurant));
    }

    @Override
    public boolean existsByNit(String nit) {
        return restaurantRepository.existsByNit(nit);
    }

    @Override
    public Long findOwnerIdByRestaurantId(Long restaurantId) {
        return restaurantRepository.findOwnerIdById(restaurantId);
    }

    @Override
    public PaginationInfo<Restaurant> listRestaurants(int page, int sizePage) {
        Pageable pageable = Pageable.ofSize(sizePage).withPage(page);
        Page<RestaurantEntity> restaurantPage = restaurantRepository.findAllByOrderByNameAsc(pageable);
        return new PaginationInfo<>(
                restaurantEntityMapper.toRestaurantList(restaurantPage.getContent()),
                restaurantPage.getNumber(),
                restaurantPage.getSize(),
                restaurantPage.getTotalElements(),
                restaurantPage.getTotalPages(),
                restaurantPage.hasNext(),
                restaurantPage.hasPrevious()
        );
    }

}
