package com.tienda.application.service.impl;

import com.tienda.application.service.IProductService;
import com.tienda.domain.model.Product;
import com.tienda.domain.port.ProductRepository;


public class DomainProductService implements IProductService {


    private final ProductRepository productRepository;

    public DomainProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<Product> getProducts() {
        return this.productRepository.getProducts();
    }

    @Override
    public Product getProductById(Long id) {
        return this.productRepository.getProduct(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return this.productRepository.saveProduct(product);
    }

    @Override
    public void deleteProduct(Long id) {
        this.productRepository.deleteProductById(id);
    }

}
