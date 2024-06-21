package com.mercadona.eanservice.repository;

import com.mercadona.eanservice.model.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ProviderRepositoryTest {

    @Autowired
    private ProviderRepository providerRepository;

    @Test
    public void whenSaveProvider_thenFindById() {
        Provider provider = new Provider();
        provider.setName("Proveedor Ejemplo");
        provider.setProviderCode("1234567");
        providerRepository.save(provider);

        Optional<Provider> foundProvider = providerRepository.findById(provider.getId());
        assertTrue(foundProvider.isPresent());
    }
}
