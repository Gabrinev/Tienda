package com.tienda.infrastructure.adaptador.impl;

import com.tienda.domain.port.CustomerRepository;
import com.tienda.infrastructure.adaptador.CustomerCrudRepositoryMySQL;
import com.tienda.infrastructure.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerRepositoryMySQL implements CustomerRepository {

    private final CustomerCrudRepositoryMySQL customerRepo;

    public CustomerRepositoryMySQL(CustomerCrudRepositoryMySQL customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Iterable<CustomerEntity> findAll() {
        return this.customerRepo.findAll();
    }

    @Override
    public Optional<CustomerEntity> findById(Long id) {
        return this.customerRepo.findById(id);
    }

    @Override
    public CustomerEntity save(CustomerEntity customer) {
        return this.customerRepo.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        this.customerRepo.deleteById(id);
    }
}
