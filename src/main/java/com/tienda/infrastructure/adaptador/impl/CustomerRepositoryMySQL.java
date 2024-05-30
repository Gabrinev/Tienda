package com.tienda.infrastructure.adaptador.impl;

import com.tienda.domain.model.Customer;
import com.tienda.domain.port.CustomerRepository;
import com.tienda.infrastructure.adaptador.CustomerCrudRepositoryMySQL;
import com.tienda.infrastructure.rest.mapper.CustomerMapper;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
public class CustomerRepositoryMySQL implements CustomerRepository {

    private final CustomerCrudRepositoryMySQL customerRepo;

    private final CustomerMapper customerMapper;

    public CustomerRepositoryMySQL(CustomerCrudRepositoryMySQL customerRepo, CustomerMapper customerMapper) {
        this.customerRepo = customerRepo;
        this.customerMapper = customerMapper;
    }

    @Override
    public Iterable<Customer> getAllCustomers() {
        return this.customerMapper.toDTO(this.customerRepo.findAll());
    }

    @Override
    public Customer getCustomer(Long id) {
        return this.customerMapper.toDTO(this.customerRepo.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return this.customerMapper.toDTO(this.customerRepo.save(this.customerMapper.toEntity(customer)));
    }

    @Override
    public void deleteCustomerById(Long id) {
        this.customerRepo.findById(id).orElseThrow(NoSuchElementException::new);
        this.customerRepo.deleteById(id);
    }
}
