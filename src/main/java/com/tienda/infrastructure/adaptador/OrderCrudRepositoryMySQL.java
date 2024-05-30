package com.tienda.infrastructure.adaptador;

import com.tienda.infrastructure.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderCrudRepositoryMySQL extends CrudRepository<OrderEntity, Long> {
}
