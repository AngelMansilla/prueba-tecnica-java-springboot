package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.model.Destination;
import com.mercadona.eanservice.service.DestinationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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

@WebMvcTest(DestinationController.class)
public class DestinationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DestinationService destinationService;

    @InjectMocks
    private DestinationController destinationController;

    private Destination destination;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(destinationController).build();
        destination = new Destination();
        destination.setId(1L);
        destination.setCode("0");
        destination.setName("Colmenas");
    }

    @Test
    public void whenGetAllDestinations_thenReturnDestinationList() throws Exception {
        when(destinationService.findAll()).thenReturn(Arrays.asList(destination));

        mockMvc.perform(get("/api/destinations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].code").value("0"))
                .andExpect(jsonPath("$[0].name").value("Colmenas"));

        verify(destinationService, times(1)).findAll();
    }

    @Test
    public void whenGetDestinationById_thenReturnDestination() throws Exception {
        when(destinationService.findById(1L)).thenReturn(Optional.of(destination));

        mockMvc.perform(get("/api/destinations/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.name").value("Colmenas"));

        verify(destinationService, times(1)).findById(1L);
    }

    @Test
    public void whenGetDestinationById_thenReturnNotFound() throws Exception {
        when(destinationService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/destinations/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(destinationService, times(1)).findById(1L);
    }

    @Test
    public void whenCreateDestination_thenReturnCreatedDestination() throws Exception {
        when(destinationService.save(any(Destination.class))).thenReturn(destination);

        mockMvc.perform(post("/api/destinations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"0\", \"name\": \"Colmenas\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.name").value("Colmenas"));

        verify(destinationService, times(1)).save(any(Destination.class));
    }

    @Test
    public void whenUpdateDestination_thenReturnUpdatedDestination() throws Exception {
        when(destinationService.findById(1L)).thenReturn(Optional.of(destination));
        when(destinationService.save(any(Destination.class))).thenReturn(destination);

        mockMvc.perform(put("/api/destinations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"0\", \"name\": \"Almacenes\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.name").value("Almacenes"));

        verify(destinationService, times(1)).findById(1L);
        verify(destinationService, times(1)).save(any(Destination.class));
    }

    @Test
    public void whenDeleteDestination_thenReturnNoContent() throws Exception {
        doNothing().when(destinationService).deleteById(1L);

        mockMvc.perform(delete("/api/destinations/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(destinationService, times(1)).deleteById(1L);
    }
}
