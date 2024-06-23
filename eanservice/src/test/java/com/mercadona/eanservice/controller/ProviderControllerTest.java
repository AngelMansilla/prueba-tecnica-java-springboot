package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.model.Provider;
import com.mercadona.eanservice.service.ProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProviderController.class)
public class ProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProviderService providerService;

    @InjectMocks
    private ProviderController providerController;

    private Provider provider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(providerController).build();
        provider = new Provider();
        provider.setId(1L);
        provider.setProviderCode("1234567");
        provider.setName("Proveedor Ejemplo");
    }

    @Test
    public void whenGetAllProviders_thenReturnProviderList() throws Exception {
        when(providerService.findAll()).thenReturn(Arrays.asList(provider));

        mockMvc.perform(get("/api/providers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].providerCode").value("1234567"))
                .andExpect(jsonPath("$[0].name").value("Proveedor Ejemplo"));

        verify(providerService, times(1)).findAll();
    }

    @Test
    public void whenGetProviderById_thenReturnProvider() throws Exception {
        when(providerService.findById(1L)).thenReturn(Optional.of(provider));

        mockMvc.perform(get("/api/providers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.providerCode").value("1234567"))
                .andExpect(jsonPath("$.name").value("Proveedor Ejemplo"));

        verify(providerService, times(1)).findById(1L);
    }

    @Test
    public void whenGetProviderById_thenReturnNotFound() throws Exception {
        when(providerService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/providers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(providerService, times(1)).findById(1L);
    }

    @Test
    public void whenCreateProvider_thenReturnCreatedProvider() throws Exception {
        when(providerService.save(any(Provider.class))).thenReturn(provider);

        mockMvc.perform(post("/api/providers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"providerCode\": \"1234567\", \"name\": \"Proveedor Ejemplo\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.providerCode").value("1234567"))
                .andExpect(jsonPath("$.name").value("Proveedor Ejemplo"));

        verify(providerService, times(1)).save(any(Provider.class));
    }

    @Test
    public void whenUpdateProvider_thenReturnUpdatedProvider() throws Exception {
        when(providerService.findById(1L)).thenReturn(Optional.of(provider));
        when(providerService.save(any(Provider.class))).thenReturn(provider);

        mockMvc.perform(put("/api/providers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"providerCode\": \"1234567\", \"name\": \"Proveedor Actualizado\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.providerCode").value("1234567"))
                .andExpect(jsonPath("$.name").value("Proveedor Actualizado"));

        verify(providerService, times(1)).findById(1L);
        verify(providerService, times(1)).save(any(Provider.class));
    }

    @Test
    public void whenDeleteProvider_thenReturnNoContent() throws Exception {
        doNothing().when(providerService).deleteById(1L);

        mockMvc.perform(delete("/api/providers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(providerService, times(1)).deleteById(1L);
    }
}
