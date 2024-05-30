package com.tienda.domain.port;

import com.tienda.infrastructure.entity.ProductEntity;

import java.util.Optional;

public interface ProductRepository {
    Iterable<ProductEntity> findAll();
    Optional<ProductEntity> findById(Long id);
    ProductEntity save(ProductEntity product);
    void deleteById(Long id);
}
