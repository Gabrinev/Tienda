package com.tienda.application.service.impl;

import com.tienda.application.service.IOrderService;
import com.tienda.domain.model.Order;
import com.tienda.domain.port.OrderRepository;
import com.tienda.infrastructure.entity.OrderEntity;
import com.tienda.infrastructure.exceptions.ResourceNotFoundException;
import com.tienda.infrastructure.rest.mapper.OrderMapper;

public class DomainOrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public DomainOrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Iterable<Order> getOrders() {
        return this.orderMapper.toDTO(this.orderRepository.findAll());
    }

    @Override
    public Order getOrderById(Long id) {
        return this.orderMapper.toDTO(this.orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Order with id: "+id+" not found")));
    }

    @Override
    public Order saveOrder(Order order) {
        OrderEntity orderEntity = this.orderMapper.toEntity(order);

        if (orderEntity.getId() != null)
            this.getOrderById(orderEntity.getId());

        return this.orderMapper.toDTO(this.orderRepository.save(orderEntity));
    }

    @Override
    public void deleteOrder(Long id) {
        this.getOrderById(id);
        this.orderRepository.deleteById(id);
    }
}
