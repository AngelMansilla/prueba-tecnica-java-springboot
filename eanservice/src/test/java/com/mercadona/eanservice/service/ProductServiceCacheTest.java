package com.mercadona.eanservice.service;

import com.mercadona.eanservice.config.CacheConfig;
import com.mercadona.eanservice.model.Product;
import com.mercadona.eanservice.repository.ProductRepository;
import com.mercadona.eanservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(CacheConfig.class)
@ContextConfiguration(classes = {ProductServiceImpl.class, CacheConfig.class})
@ComponentScan(basePackages = "com.mercadona.eanservice")
public class ProductServiceCacheTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Limpiar cache si existe
        Optional.ofNullable(cacheManager.getCache("products")).ifPresent(Cache::clear);
    }

    @Test
    public void whenFindById_thenCacheProduct() {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Primera llamada usando repository
        Optional<Product> product1 = productService.findByIdRaw(1L);
        assertTrue(product1.isPresent());

        // Comprobamos que el producto esta en cache
        Cache cache = cacheManager.getCache("products");
        assertTrue(cache != null && cache.get(1L) != null, "Product should be cached after first call");

        // Segunda llamada usando cache
        Optional<Product> product2 = productService.findByIdRaw(1L);
        assertTrue(product2.isPresent());

        // Comprobamos la iteraciones con el repositorio
        verify(productRepository, times(1)).findById(1L);
    }

    @Configuration
    @EnableCaching
    static class Config {
    }
}
