package com.tienda.infrastructure;

import com.tienda.domain.port.CustomerRepository;
import com.tienda.domain.port.OrderRepository;
import com.tienda.infrastructure.entity.CustomerEntity;
import com.tienda.infrastructure.entity.OrderEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public DataInitializer(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    public void init() {

        CustomerEntity customerEntity = new CustomerEntity(1L, "gabriele", "nevini", "fake.mail@example.com", "1234567890", "123 Main St");

        OrderEntity orderEntity = new OrderEntity(1L, LocalDateTime.now(), "pending", 12D, customerEntity);
        OrderEntity orderEntity2 = new OrderEntity(2L, LocalDateTime.now(), "delivered", 2D, customerEntity);

        customerRepository.save(customerEntity);
        // TODO: SAVEALL
        orderRepository.save(orderEntity);
        orderRepository.save(orderEntity2);
    }
}
