package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.dto.ProductDTO;
import com.mercadona.eanservice.service.EanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EanControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EanService eanService;

    private EanController eanController;

    private ProductDTO productDTO;

    @BeforeEach
    public void setUp() {
        eanController = new EanController(eanService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(eanController).build();
        productDTO = new ProductDTO(1L, "1234567123451", "Producto Ejemplo", "Proveedor Ejemplo",
                "Tiendas Mercadona España");
    }

    @Test
    public void whenGetProductByEan_thenReturnProductDTO() throws Exception {
        when(eanService.findByEan("1234567123451")).thenReturn(Optional.of(productDTO));

        mockMvc.perform(get("/api/ean/1234567123451")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ean").value("1234567123451"))
                .andExpect(jsonPath("$.name").value("Producto Ejemplo"))
                .andExpect(jsonPath("$.providerName").value("Proveedor Ejemplo"))
                .andExpect(jsonPath("$.destinationName").value("Tiendas Mercadona España"));

        verify(eanService, times(1)).findByEan("1234567123451");
    }

    @Test
    public void whenGetProductByEan_thenReturnNotFound() throws Exception {
        when(eanService.findByEan("1234567123451")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/ean/1234567123451")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(eanService, times(1)).findByEan("1234567123451");
    }
}
