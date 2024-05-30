package com.tienda.application.service;

import com.tienda.domain.model.Order;

public interface IOrderService {

    Iterable<Order> getOrders();
    Order getOrderById (Long id);
    Order saveOrder (Order order);
    void deleteOrder(Long id);
}
