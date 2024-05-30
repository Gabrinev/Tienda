package com.tienda.domain.port;

import com.tienda.infrastructure.entity.OrderEntity;

import java.util.Optional;

public interface OrderRepository {
    Iterable<OrderEntity> findAll();
    Optional<OrderEntity> findById(Long id);
    OrderEntity save(OrderEntity order);
    void deleteById(Long id);
}
