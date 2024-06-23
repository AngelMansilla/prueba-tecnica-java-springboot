package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.dto.ProductDTO;
import com.mercadona.eanservice.model.Product;
import com.mercadona.eanservice.model.Provider;
import com.mercadona.eanservice.model.Destination;
import com.mercadona.eanservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product;
    private Provider provider;
    private Destination destination;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        provider = new Provider();
        provider.setName("Proveedor Ejemplo");
        provider.setProviderCode("1234567");

        destination = new Destination();
        destination.setName("Destino Ejemplo");
        destination.setCode("1");

        product = new Product();
        product.setId(1L);  // Asegúrate de que el ID esté configurado
        product.setEan("1234567123451");
        product.setProvider(provider);
        product.setDestination(destination);
    }

    @Test
    public void whenGetProductById_thenReturnProductDTO() throws Exception {
        ProductDTO productDTO = ProductDTO.fromProduct(product);
        when(productService.findById(product.getId())).thenReturn(Optional.of(productDTO));

        mockMvc.perform(get("/api/products/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.ean").value(product.getEan()))
                .andExpect(jsonPath("$.providerName").value(provider.getName()))
                .andExpect(jsonPath("$.destinationName").value(destination.getName()));

        verify(productService, times(1)).findById(product.getId());
    }

    @Test
    public void whenGetProductById_thenReturnNotFound() throws Exception {
        when(productService.findById(product.getId())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).findById(product.getId());
    }
}
