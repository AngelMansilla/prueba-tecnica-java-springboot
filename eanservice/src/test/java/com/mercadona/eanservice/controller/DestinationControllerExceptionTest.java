package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.exception.DestinationNotFoundException;
import com.mercadona.eanservice.exception.GlobalExceptionHandler;
import com.mercadona.eanservice.service.DestinationService;
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
public class DestinationControllerExceptionTest {

    @Mock
    private DestinationService destinationService;

    private DestinationController destinationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        destinationController = new DestinationController(destinationService);
        mockMvc = MockMvcBuilders.standaloneSetup(destinationController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void whenGetDestinationById_thenThrowDestinationNotFoundException() throws Exception {
        when(destinationService.findById(1L)).thenThrow(new DestinationNotFoundException("Destination not found"));

        mockMvc.perform(get("/api/destinations/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
