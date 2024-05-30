package com.tienda.infrastructure.adaptador.impl;

import com.tienda.domain.port.OrderRepository;
import com.tienda.infrastructure.adaptador.OrderCrudRepositoryMySQL;
import com.tienda.infrastructure.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderRepositoryMySQL implements OrderRepository {

    private final OrderCrudRepositoryMySQL orderRepo;

    public OrderRepositoryMySQL(OrderCrudRepositoryMySQL orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public Iterable<OrderEntity> findAll() {
        return this.orderRepo.findAll();
    }

    @Override
    public Optional<OrderEntity> findById(Long id) {
        return this.orderRepo.findById(id);
    }

    @Override
    public OrderEntity save(OrderEntity order) {
        return this.orderRepo.save(order);
    }

    @Override
    public void deleteById(Long id) {
        this.orderRepo.deleteById(id);
    }
}
