package com.tienda.infrastructure.adaptador.impl;

import com.tienda.domain.port.ProductRepository;
import com.tienda.infrastructure.adaptador.ProductCrudRepositoryMySQL;
import com.tienda.infrastructure.entity.ProductEntity;
import com.tienda.infrastructure.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductRepositoryMySQL implements ProductRepository {

    private final ProductCrudRepositoryMySQL productRepo;
    
    public ProductRepositoryMySQL(ProductCrudRepositoryMySQL productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Iterable<ProductEntity> findAll() {
        return this.productRepo.findAll();
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return this.productRepo.findById(id);
    }

    @Override
    public ProductEntity save(ProductEntity product) {
        return this.productRepo.save(product);
    }

    @Override
    public void deleteById(Long id) {
        this.productRepo.deleteById(id);
    }
}
