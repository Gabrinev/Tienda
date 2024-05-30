package com.tienda.infrastructure.adaptador;

import com.tienda.infrastructure.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerCrudRepositoryMySQL extends CrudRepository<CustomerEntity, Long> {

}
