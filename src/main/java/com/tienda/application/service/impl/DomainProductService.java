package com.tienda.application.service.impl;

import com.tienda.application.service.IProductService;
import com.tienda.domain.model.Product;
import com.tienda.domain.port.ProductRepository;
import com.tienda.infrastructure.entity.ProductEntity;
import com.tienda.infrastructure.exceptions.ResourceNotFoundException;
import com.tienda.infrastructure.rest.mapper.ProductMapper;


public class DomainProductService implements IProductService {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public DomainProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Iterable<Product> getProducts() {
        return this.productMapper.toDTO(this.productRepository.findAll());
    }

    @Override
    public Product getProductById(Long id) {
        return this.productMapper.toDTO(this.productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product with id: "+id+" not found")));
    }

    @Override
    public Product saveProduct(Product product) {
        ProductEntity productEntity = this.productMapper.toEntity(product);

        if (productEntity.getId() != null)
            this.getProductById(productEntity.getId());

        return this.productMapper.toDTO(this.productRepository.save(productEntity));
    }

    @Override
    public void deleteProduct(Long id) {
        this.getProductById(id);
        this.productRepository.deleteById(id);
    }

}
