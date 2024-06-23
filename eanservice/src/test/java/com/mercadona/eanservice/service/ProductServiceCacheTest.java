package com.mercadona.eanservice.service;

import com.mercadona.eanservice.model.Product;
import com.mercadona.eanservice.repository.ProductRepository;
import com.mercadona.eanservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class ProductServiceCacheTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    public void whenFindById_thenCacheProduct() {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> product1 = productService.findByIdRaw(1L);
        Optional<Product> product2 = productService.findByIdRaw(1L);

        verify(productRepository, times(1)).findById(1L);
        assertTrue(product1.isPresent());
        assertTrue(product2.isPresent());
    }

    @Configuration
    @EnableCaching
    static class Config {
    }
}
