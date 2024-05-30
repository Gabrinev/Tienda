package com.tienda.infrastructure.config;

import com.tienda.application.service.ICustomerService;
import com.tienda.application.service.IOrderService;
import com.tienda.application.service.IProductService;
import com.tienda.application.service.impl.DomainCustomerService;
import com.tienda.application.service.impl.DomainOrderService;
import com.tienda.application.service.impl.DomainProductService;
import com.tienda.domain.port.CustomerRepository;
import com.tienda.domain.port.OrderRepository;
import com.tienda.domain.port.ProductRepository;
import com.tienda.infrastructure.rest.mapper.CustomerMapper;
import com.tienda.infrastructure.rest.mapper.OrderMapper;
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
    OrderMapper orderMapper(CustomerMapper customerMapper){
        return new OrderMapper(customerMapper);
    }

    @Bean
    ICustomerService customerBeanService(final CustomerRepository customerRepository, CustomerMapper customerMapper){
        return new DomainCustomerService(customerRepository, customerMapper);
    }
    @Bean
    IOrderService orderBeanService(final OrderRepository orderRepository, OrderMapper orderMapper){
        return new DomainOrderService(orderRepository, orderMapper);
    }
    @Bean
    IProductService productBeanService(final ProductRepository productRepository, ProductMapper productMapper){
        return new DomainProductService(productRepository, productMapper);
    }
}
