package com.tienda.domain.port;

import com.tienda.infrastructure.entity.CustomerEntity;

import java.util.Optional;

public interface CustomerRepository {
    Iterable<CustomerEntity> findAll();
    Optional<CustomerEntity> findById(Long id);
    CustomerEntity save(CustomerEntity customer);
    void deleteById(Long id);
}
