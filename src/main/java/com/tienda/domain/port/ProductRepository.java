package com.tienda.domain.port;

import com.tienda.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {
    Iterable<Product> getProducts();
    Product getProduct (Long id);
    Product saveProduct (Product product);
    void deleteProductById(Long id);
}
