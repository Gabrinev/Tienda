package com.tienda.infrastructure.adaptador.impl;

import com.tienda.domain.model.Product;
import com.tienda.domain.port.ProductRepository;
import com.tienda.infrastructure.adaptador.ProductCrudRepositoryMySQL;
import com.tienda.infrastructure.rest.mapper.ProductMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryMySQL implements ProductRepository {

    private final ProductCrudRepositoryMySQL productRepo;

    private final ProductMapper productMapper;

    public ProductRepositoryMySQL(ProductCrudRepositoryMySQL productRepo, ProductMapper productMapper) {
        this.productRepo = productRepo;
        this.productMapper = productMapper;
    }

    @Override
    public Iterable<Product> getProducts() {
        return null;
    }

    @Override
    public Product getProduct(Long id) {
        return null;
    }

    @Override
    public Product saveProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProductById(Long id) {

    }
}
