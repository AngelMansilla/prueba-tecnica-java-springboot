package com.mercadona.eanservice.service;

import com.mercadona.eanservice.model.Destination;
import com.mercadona.eanservice.repository.DestinationRepository;
import com.mercadona.eanservice.service.impl.DestinationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class DestinationServiceTest {

    @Mock
    private DestinationRepository destinationRepository;

    @InjectMocks
    private DestinationServiceImpl destinationService;

    private Destination destination;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        destination = new Destination();
        destination.setName("Destino Ejemplo");
        destination.setCode("1");
    }

    @Test
    public void whenSaveDestination_thenDestinationIsSaved() {
        when(destinationRepository.save(destination)).thenReturn(destination);
        Destination savedDestination = destinationService.save(destination);
        verify(destinationRepository, times(1)).save(destination);
        assertEquals(destination, savedDestination);
    }

    @Test
    public void whenFindById_thenDestinationIsFound() {
        when(destinationRepository.findById(destination.getId())).thenReturn(Optional.of(destination));
        Optional<Destination> foundDestination = destinationService.findById(destination.getId());
        verify(destinationRepository, times(1)).findById(destination.getId());
        assertTrue(foundDestination.isPresent());
        assertEquals(destination, foundDestination.get());
    }

    @Test
    public void whenDeleteById_thenDestinationIsDeleted() {
        doNothing().when(destinationRepository).deleteById(destination.getId());
        destinationService.deleteById(destination.getId());
        verify(destinationRepository, times(1)).deleteById(destination.getId());
    }
}
