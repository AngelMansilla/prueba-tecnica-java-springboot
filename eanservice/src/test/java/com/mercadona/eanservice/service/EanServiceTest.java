package com.mercadona.eanservice.service;

import com.mercadona.eanservice.model.Product;
import com.mercadona.eanservice.model.Provider;
import com.mercadona.eanservice.model.Destination;
import com.mercadona.eanservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class EanServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private EanServiceImpl eanService;

    private Product product;
    private Provider provider;
    private Destination destination;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        provider = new Provider();
        provider.setName("Proveedor Ejemplo");
        provider.setProviderCode("1234567");

        destination = new Destination();
        destination.setName("Destino Ejemplo");
        destination.setCode("1");

        product = new Product();
        product.setEan("1234567123451");
        product.setProvider(provider);
        product.setDestination(destination);
    }

    @Test
    public void whenFindByEan_thenProductIsFound() {
        when(productRepository.findByEan(product.getEan())).thenReturn(Optional.of(product));
        Optional<Product> foundProduct = eanService.findByEan(product.getEan());
        verify(productRepository, times(1)).findByEan(product.getEan());
        assertTrue(foundProduct.isPresent());
        assertEquals(product, foundProduct.get());
    }

    @Test
    public void whenFindByEan_thenProductNotFound() {
        when(productRepository.findByEan(product.getEan())).thenReturn(Optional.empty());
        Optional<Product> foundProduct = eanService.findByEan(product.getEan());
        verify(productRepository, times(1)).findByEan(product.getEan());
        assertTrue(foundProduct.isEmpty());
    }
}
