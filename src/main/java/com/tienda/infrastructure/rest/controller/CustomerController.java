package com.tienda.infrastructure.rest.controller;

import com.tienda.application.service.ICustomerService;
import com.tienda.domain.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Customer>> getCustomers() {
        return new ResponseEntity<>(this.customerService.getCustomers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id){
        return new ResponseEntity<>(this.customerService.getCustomerById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id){
        this.customerService.deleteCustomer(id);
    }

}
