package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.dto.ProductDTO;
import com.mercadona.eanservice.model.Product;
import com.mercadona.eanservice.model.Provider;
import com.mercadona.eanservice.model.Destination;
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

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private EanService eanService;

    private ProductController productController;

    private Product product;
    private Provider provider;
    private Destination destination;
    private ProductDTO productDTO;

    @BeforeEach
    public void setUp() {
        productController = new ProductController(productService, eanService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        destination = new Destination();
        destination.setId(1L);
        destination.setName("Tiendas Mercadona España");

        provider = new Provider();
        provider.setId(1L);
        provider.setName("Proveedor Ejemplo");

        product = new Product();
        product.setId(1L);
        product.setEan("1234567123451");
        product.setName("Producto Ejemplo");
        product.setProvider(provider);
        product.setDestination(destination);

        productDTO = new ProductDTO(product.getId(), product.getEan(), product.getName(), provider.getName(),
                destination.getName());
    }

    @Test
    public void whenGetProductById_thenReturnProductDTO() throws Exception {
        when(productService.findById(product.getId())).thenReturn(Optional.of(productDTO));

        mockMvc.perform(get("/api/products/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.ean").value(product.getEan()))
                .andExpect(jsonPath("$.name").value(product.getName()))
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

    @Test
    public void whenGetAllProducts_thenReturnProductList() throws Exception {
        when(productService.findAll()).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(product.getId()))
                .andExpect(jsonPath("$[0].ean").value(product.getEan()))
                .andExpect(jsonPath("$[0].name").value(product.getName()));

        verify(productService, times(1)).findAll();
    }

    @Test
    public void whenCreateProduct_thenReturnCreatedProduct() throws Exception {
        when(productService.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ean\": \"1234567123451\", \"name\": \"Producto Ejemplo\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.ean").value("1234567123451"))
                .andExpect(jsonPath("$.name").value("Producto Ejemplo"));

        verify(productService, times(1)).save(any(Product.class));
    }

    @Test
    public void whenUpdateProduct_thenReturnUpdatedProduct() throws Exception {
        Product updatedProduct = new Product();
        updatedProduct.setId(product.getId());
        updatedProduct.setEan(product.getEan());
        updatedProduct.setName("Producto Actualizado");
        updatedProduct.setProvider(product.getProvider());
        updatedProduct.setDestination(product.getDestination());

        when(productService.findByIdRaw(product.getId())).thenReturn(Optional.of(product));
        when(productService.save(any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/products/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"ean\": \"1234567123451\", \"name\": \"Producto Actualizado\", \"provider\": {\"providerCode\": \"1234567\", \"name\": \"Proveedor Ejemplo\"}, \"destination\": {\"code\": \"1\", \"name\": \"Destino Ejemplo\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.ean").value("1234567123451"))
                .andExpect(jsonPath("$.name").value("Producto Actualizado"));

        verify(productService, times(1)).findByIdRaw(product.getId());
        verify(productService, times(1)).save(any(Product.class));
    }

    @Test
    public void whenDeleteProduct_thenReturnNoContent() throws Exception {
        doNothing().when(productService).deleteById(1L);

        mockMvc.perform(delete("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteById(1L);
    }
}
