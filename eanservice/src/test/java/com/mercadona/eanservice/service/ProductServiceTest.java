package com.mercadona.eanservice.service;

import com.mercadona.eanservice.dto.ProductDTO;
import com.mercadona.eanservice.model.Product;
import com.mercadona.eanservice.model.Provider;
import com.mercadona.eanservice.model.Destination;
import com.mercadona.eanservice.repository.ProductRepository;
import com.mercadona.eanservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

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
    public void whenSaveProduct_thenProductIsSaved() {
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        verify(productRepository, times(1)).save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    public void whenFindById_thenProductIsFound() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        Optional<ProductDTO> foundProduct = productService.findById(product.getId());
        verify(productRepository, times(1)).findById(product.getId());
        assertTrue(foundProduct.isPresent());
        assertEquals(product.getEan(), foundProduct.get().getEan());
        assertEquals(provider.getName(), foundProduct.get().getProviderName());
        assertEquals(destination.getName(), foundProduct.get().getDestinationName());
    }

    @Test
    public void whenDeleteById_thenProductIsDeleted() {
        doNothing().when(productRepository).deleteById(product.getId());
        productService.deleteById(product.getId());
        verify(productRepository, times(1)).deleteById(product.getId());
    }
}
