package com.tienda.application.service.impl;

import com.tienda.application.service.ICustomerService;
import com.tienda.domain.model.Customer;
import com.tienda.domain.port.CustomerRepository;
import com.tienda.infrastructure.entity.CustomerEntity;
import com.tienda.infrastructure.exceptions.ResourceNotFoundException;
import com.tienda.infrastructure.rest.mapper.CustomerMapper;


public class DomainCustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public DomainCustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Iterable<Customer> getCustomers() {
        return this.customerMapper.toDTO(this.customerRepository.findAll());
    }

    @Override
    public Customer getCustomerById(Long id) {
        return this.customerMapper.toDTO(this.customerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Customer with id: " + id + " not found")));
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        CustomerEntity customerEntity = this.customerMapper.toEntity(customer);

        if (customerEntity.getId() != null)
            this.getCustomerById(customerEntity.getId());

        return this.customerMapper.toDTO(this.customerRepository.save(customerEntity));
    }

    @Override
    public void deleteCustomer(Long id) {
        this.getCustomerById(id);
        this.customerRepository.deleteById(id);
    }
}
