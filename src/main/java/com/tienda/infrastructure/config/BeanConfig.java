package com.tienda.infrastructure.config;

import com.tienda.application.service.ICustomerService;
import com.tienda.application.service.IProductService;
import com.tienda.application.service.impl.DomainCustomerService;
import com.tienda.application.service.impl.DomainProductService;
import com.tienda.domain.port.CustomerRepository;
import com.tienda.domain.port.ProductRepository;
import com.tienda.infrastructure.rest.mapper.CustomerMapper;
import com.tienda.infrastructure.rest.mapper.ProductMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    CustomerMapper customerMapper(){
        return new CustomerMapper();
    }
    @Bean
    ProductMapper productMapper(){
        return new ProductMapper();
    }

    @Bean
    ICustomerService customerBeanService(final CustomerRepository customerRepository){
        return new DomainCustomerService(customerRepository);
    }
    @Bean
    IProductService productBeanService(final ProductRepository productRepository){
        return new DomainProductService(productRepository);
    }
}
