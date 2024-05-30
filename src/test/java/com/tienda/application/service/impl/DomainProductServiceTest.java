package com.tienda.application.service.impl;

import com.tienda.domain.model.Product;
import com.tienda.domain.port.ProductRepository;
import com.tienda.infrastructure.entity.ProductEntity;
import com.tienda.infrastructure.exceptions.ResourceNotFoundException;
import com.tienda.infrastructure.rest.mapper.ProductMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DomainProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductMapper productMapper;

    @InjectMocks
    DomainProductService productService;

    @Nested
    class GetProducts {

        @Test
        void getProducts_SuccessfulCase_ReturnsProductIterable() {

            Iterable<ProductEntity> productEntities = Arrays.asList(
                    new ProductEntity(1L, "Product 1", "Description 1", "Category 1", 10.0, "M", "Red", 100, null),
                    new ProductEntity(2L, "Product 2", "Description 2", "Category 2", 20.0, "L", "Blue", 200, null)
            );
            Iterable<Product> productDtos = Arrays.asList(
                    new Product(1L, "Product 1", "Description 1", "Category 1", 10.0, "M", "Red", 100, null),
                    new Product(2L, "Product 2", "Description 2", "Category 2", 20.0, "L", "Blue", 200, null)
            );


            when(productRepository.findAll()).thenReturn(productEntities);
            when(productMapper.toDTO(productEntities)).thenReturn(productDtos);


            Iterable<Product> products = productService.getProducts();

            assertNotNull(products);
            List<Product> productList = (List<Product>) products;
            assertEquals(2, productList.size());
            assertEquals("Product 1", productList.get(0).getName());
            assertEquals("Product 2", productList.get(1).getName());
        }
    }

    @Nested
    class GetProductById {

        @Test
        void getProductById_SuccessfulCase_ReturnsProduct() {

            Long validId = 1L;
            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(validId);
            productEntity.setName("Test Product");
            productEntity.setDescription("Test Description");
            productEntity.setCategory("Test Category");
            productEntity.setPrice(100.0);
            productEntity.setSize("M");
            productEntity.setColor("Red");
            productEntity.setStock(10);
            productEntity.setPicture(new byte[]{});

            Product productDto = new Product();
            productDto.setId(validId);
            productDto.setName("Test Product");
            productDto.setDescription("Test Description");
            productDto.setCategory("Test Category");
            productDto.setPrice(100.0);
            productDto.setSize("M");
            productDto.setColor("Red");
            productDto.setStock(10);
            productDto.setPicture(new byte[]{});


            when(productRepository.findById(validId)).thenReturn(Optional.of(productEntity));
            when(productMapper.toDTO(productEntity)).thenReturn(productDto);


            Product result = productService.getProductById(validId);

            assertNotNull(result);
            assertEquals(validId, result.getId());
            assertEquals("Test Product", result.getName());
            assertEquals("Test Description", result.getDescription());
            assertEquals("Test Category", result.getCategory());
            assertEquals(100.0, result.getPrice(), 0.01);
            assertEquals("M", result.getSize());
            assertEquals("Red", result.getColor());
            assertEquals(10, result.getStock().intValue());
        }

        @Test
        void getProductById_IdNotFound_ThenReturnResourceNotFoundException() {
            Long nonExistentId = 100L;

            when(productRepository.findById(nonExistentId)).thenThrow(ResourceNotFoundException.class);

            assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(nonExistentId));
        }
    }

    @Nested
    class SaveProduct {

        @Test
        void saveProduct_SuccessfulInsertCase_ReturnsProduct() {

            Product product = new Product(null, "Test Product", "Description", "Category", 100.0, "M", "Red", 10, null);
            Product savedProduct = new Product(1L, "Test Product", "Description", "Category", 100.0, "M", "Red", 10, null);
            ProductEntity productEntity = new ProductEntity(null, "Test Product", "Description", "Category", 100.0, "M", "Red", 10, null);
            ProductEntity savedProductEntity = new ProductEntity(1L, "Test Product", "Description", "Category", 100.0, "M", "Red", 10, null);

            when(productRepository.save(productEntity)).thenReturn(savedProductEntity);
            when(productMapper.toEntity(product)).thenReturn(productEntity);
            when(productMapper.toDTO(savedProductEntity)).thenReturn(savedProduct);

            Product returnedProduct = productService.saveProduct(product);

            assertNotNull(returnedProduct.getId());
            assertEquals("Test Product", returnedProduct.getName());
            verify(productRepository, times(1)).save(productEntity);
        }

        @Test
        void saveProduct_SuccessfulUpdateCase_ReturnsProduct() {

            Product product = new Product(1L, "Test Product", "Description", "Category", 100.0, "M", "Red", 10, null);
            Product modedProduct = new Product(1L, "Test Product", "MODIFIED DESCRIPTION", "Category", 100.0, "M", "Red", 10, null);
            ProductEntity productEntity = new ProductEntity(1L, "Test Product", "Description", "Category", 100.0, "M", "Red", 10, null);
            ProductEntity modedProductEntity = new ProductEntity(1L, "Test Product", "MODIFIED DESCRIPTION", "Category", 100.0, "M", "Red", 10, null);

            when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));
            when(productRepository.save(productEntity)).thenReturn(modedProductEntity);
            when(productMapper.toEntity(product)).thenReturn(productEntity);
            when(productMapper.toDTO(modedProductEntity)).thenReturn(modedProduct);
            when(productMapper.toDTO(productEntity)).thenReturn(product);

            Product returnedProduct = productService.saveProduct(product);

            assertNotNull(returnedProduct.getId());
            assertEquals("Test Product", returnedProduct.getName());
            assertEquals("MODIFIED DESCRIPTION", returnedProduct.getDescription());
            verify(productRepository, times(1)).save(productEntity);
        }

    }

    @Nested
    class DeleteProduct {

        @Test
        void deleteProduct_SuccessfulCase_ReturnsVoid() {

            Long id = 1L;
            ProductEntity productEntity = new ProductEntity(1L, "Test Product", "Description", "Category", 100.0, "M", "Red", 10, null);


            when(productRepository.findById(id)).thenReturn(Optional.of(productEntity));
            doNothing().when(productRepository).deleteById(id);

            productService.deleteProduct(id);

            verify(productRepository, times(1)).deleteById(id);
        }

        @Test
        void deleteProduct_IdNotFound_ReturnsResourceNotFoundException() {

            Long nonExistentId = 100L;

            when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(nonExistentId));
        }
    }


}