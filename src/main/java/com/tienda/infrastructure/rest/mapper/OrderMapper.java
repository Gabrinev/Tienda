package com.tienda.infrastructure.rest.mapper;

import com.tienda.domain.model.Order;
import com.tienda.infrastructure.entity.OrderEntity;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    private final CustomerMapper customerMapper;

    public OrderMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public Order toDTO(OrderEntity orderEntity) {
        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setDate(orderEntity.getDate());
        order.setStatus(orderEntity.getStatus());
        order.setTotal(orderEntity.getTotal());
        if (orderEntity.getCustomer() != null)
            order.setCustomer(this.customerMapper.toDTO(orderEntity.getCustomer()));
        return order;
    }

    public Iterable<Order> toDTO(Iterable<OrderEntity> entities) {
        List<Order> dtoList = new ArrayList<>();
        for (OrderEntity entity : entities) {
            Order dto = this.toDTO(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public OrderEntity toEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setDate(order.getDate());
        orderEntity.setStatus(order.getStatus());
        orderEntity.setTotal(order.getTotal());
        if (order.getCustomer() != null)
            orderEntity.setCustomer(this.customerMapper.toEntity(order.getCustomer()));
        return orderEntity;
    }

    public Iterable<OrderEntity> toEntity(Iterable<Order> dtos) {
        List<OrderEntity> entityList = new ArrayList<>();
        for (Order dto : dtos) {
            OrderEntity entity = this.toEntity(dto);
            entityList.add(entity);
        }
        return entityList;
    }
}
