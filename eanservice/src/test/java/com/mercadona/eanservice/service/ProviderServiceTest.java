package com.mercadona.eanservice.service;

import com.mercadona.eanservice.model.Provider;
import com.mercadona.eanservice.repository.ProviderRepository;
import com.mercadona.eanservice.service.impl.ProviderServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ProviderServiceTest {

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderServiceImpl providerService;

    private Provider provider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        provider = new Provider();
        provider.setName("Proveedor Ejemplo");
        provider.setProviderCode("1234567");
    }

    @Test
    public void whenSaveProvider_thenProviderIsSaved() {
        when(providerRepository.save(provider)).thenReturn(provider);
        Provider savedProvider = providerService.save(provider);
        verify(providerRepository, times(1)).save(provider);
        assertEquals(provider, savedProvider);
    }

    @Test
    public void whenFindById_thenProviderIsFound() {
        when(providerRepository.findById(provider.getId())).thenReturn(Optional.of(provider));
        Optional<Provider> foundProvider = providerService.findById(provider.getId());
        verify(providerRepository, times(1)).findById(provider.getId());
        assertTrue(foundProvider.isPresent());
        assertEquals(provider, foundProvider.get());
    }

    @Test
    public void whenDeleteById_thenProviderIsDeleted() {
        doNothing().when(providerRepository).deleteById(provider.getId());
        providerService.deleteById(provider.getId());
        verify(providerRepository, times(1)).deleteById(provider.getId());
    }
}
