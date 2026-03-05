package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.exception.GlobalExceptionHandler;
import com.mercadona.eanservice.exception.ProductNotFoundException;
import com.mercadona.eanservice.service.EanService;
import com.mercadona.eanservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductControllerExceptionTest {

    @Mock
    private ProductService productService;

    @Mock
    private EanService eanService;

    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        productController = new ProductController(productService, eanService);
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void whenGetProductById_thenThrowProductNotFoundException() throws Exception {
        when(productService.findById(1L)).thenThrow(new ProductNotFoundException("Product not found"));

        mockMvc.perform(get("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
