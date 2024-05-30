package com.tienda.infrastructure.rest.mapper;

import com.tienda.domain.model.Product;
import com.tienda.infrastructure.entity.ProductEntity;

public class ProductMapper {

    public Product toDTO(ProductEntity productEntity) {
        Product product = new Product();
        product.setId(productEntity.getId());
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setCategory(productEntity.getCategory());
        product.setPrice(productEntity.getPrice());
        product.setSize(productEntity.getSize());
        product.setColor(productEntity.getColor());
        product.setStock(productEntity.getStock());
        product.setPicture(productEntity.getPicture());
        return product;
    }

    public ProductEntity toEntity(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setCategory(product.getCategory());
        productEntity.setPrice(product.getPrice());
        productEntity.setSize(product.getSize());
        productEntity.setColor(product.getColor());
        productEntity.setStock(product.getStock());
        productEntity.setPicture(product.getPicture());
        return productEntity;
    }
}
