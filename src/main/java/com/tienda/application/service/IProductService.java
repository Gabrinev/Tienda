package com.tienda.application.service;

import com.tienda.domain.model.Product;

public interface IProductService {
    Iterable<Product> getProducts();
    Product getProductById (Long id);
    Product saveProduct (Product product);
    void deleteProduct(Long id);
}
