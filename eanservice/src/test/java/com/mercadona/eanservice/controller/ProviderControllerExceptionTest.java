package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.exception.ProviderNotFoundException;
import com.mercadona.eanservice.service.ProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProviderControllerExceptionTest {

    @Mock
    private ProviderService providerService;

    @InjectMocks
    private ProviderController providerController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(providerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void whenGetProviderById_thenThrowProviderNotFoundException() throws Exception {
        when(providerService.findById(1L)).thenThrow(new ProviderNotFoundException("Provider not found"));

        mockMvc.perform(get("/api/providers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
