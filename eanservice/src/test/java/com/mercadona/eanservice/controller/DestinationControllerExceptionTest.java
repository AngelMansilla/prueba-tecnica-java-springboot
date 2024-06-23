package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.exception.DestinationNotFoundException;
import com.mercadona.eanservice.service.DestinationService;
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
public class DestinationControllerExceptionTest {

    @Mock
    private DestinationService destinationService;

    @InjectMocks
    private DestinationController destinationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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
