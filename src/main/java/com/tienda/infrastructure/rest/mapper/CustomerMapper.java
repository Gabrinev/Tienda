package com.tienda.infrastructure.rest.mapper;

import com.tienda.domain.model.Customer;
import com.tienda.infrastructure.entity.CustomerEntity;

import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {
    public Customer toDTO(CustomerEntity customerEntity) {
        Customer customer = new Customer();
        customer.setId(customerEntity.getId());
        customer.setName(customerEntity.getName());
        customer.setSurname(customerEntity.getSurname());
        customer.setEmail(customerEntity.getEmail());
        customer.setPhoneNumber(customerEntity.getPhoneNumber());
        customer.setAddress(customerEntity.getAddress());
        return customer;
    }

    public Iterable<Customer> toDTO(Iterable<CustomerEntity> entities) {
        List<Customer> dtoList = new ArrayList<>();
        for (CustomerEntity entity : entities) {
            Customer dto = this.toDTO(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public CustomerEntity toEntity(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customer.getId());
        customerEntity.setName(customer.getName());
        customerEntity.setSurname(customer.getSurname());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setPhoneNumber(customer.getPhoneNumber());
        customerEntity.setAddress(customer.getAddress());
        return customerEntity;
    }

    public Iterable<CustomerEntity> toEntity(Iterable<Customer> dtos) {
        List<CustomerEntity> entityList = new ArrayList<>();
        for (Customer dto : dtos) {
            CustomerEntity entity = this.toEntity(dto);
            entityList.add(entity);
        }
        return entityList;
    }

}
