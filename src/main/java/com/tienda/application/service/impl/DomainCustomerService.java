package com.tienda.application.service.impl;

import com.tienda.application.service.ICustomerService;
import com.tienda.domain.model.Customer;
import com.tienda.domain.port.CustomerRepository;


public class DomainCustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    public DomainCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Iterable<Customer> getCustomers() {
        return this.customerRepository.getAllCustomers();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return this.customerRepository.getCustomer(id);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return this.customerRepository.saveCustomer(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        this.customerRepository.deleteCustomerById(id);
    }
}
