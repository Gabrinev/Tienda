package com.tienda.infrastructure.adaptador;

import com.tienda.infrastructure.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductCrudRepositoryMySQL extends CrudRepository<ProductEntity, Long> {

}
