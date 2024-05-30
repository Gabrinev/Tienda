package com.tienda.application.service;

import com.tienda.domain.model.Customer;

public interface ICustomerService {
    Iterable<Customer> getCustomers();
    Customer getCustomerById (Long id);
    Customer saveCustomer (Customer customer);
    void deleteCustomer(Long id);
}
