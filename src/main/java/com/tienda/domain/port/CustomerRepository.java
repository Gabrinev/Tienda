package com.tienda.domain.port;

import com.tienda.domain.model.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Iterable<Customer> getAllCustomers();
    Customer getCustomer(Long id);
    Customer saveCustomer(Customer product);
    void deleteCustomerById(Long id);
}
