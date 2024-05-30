package com.tienda.infrastructure.rest.mapper;

import com.tienda.domain.model.Customer;
import com.tienda.domain.model.Product;
import com.tienda.infrastructure.entity.CustomerEntity;
import com.tienda.infrastructure.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;

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

    public Iterable<Product> toDTO(Iterable<ProductEntity> entities) {
        List<Product> dtoList = new ArrayList<>();
        for (ProductEntity entity : entities) {
            Product dto = this.toDTO(entity);
            dtoList.add(dto);
        }
        return dtoList;
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

    public Iterable<ProductEntity> toEntity(Iterable<Product> dtos) {
        List<ProductEntity> entityList = new ArrayList<>();
        for (Product dto : dtos) {
            ProductEntity entity = this.toEntity(dto);
            entityList.add(entity);
        }
        return entityList;
    }


}
