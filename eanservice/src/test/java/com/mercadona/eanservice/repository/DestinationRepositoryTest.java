package com.mercadona.eanservice.repository;

import com.mercadona.eanservice.model.Destination;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class DestinationRepositoryTest {

    @Autowired
    private DestinationRepository destinationRepository;

    @Test
    public void whenSaveDestination_thenFindById() {
        Destination destination = new Destination();
        destination.setName("Destino Ejemplo");
        destination.setCode("1");
        destinationRepository.save(destination);

        Optional<Destination> foundDestination = destinationRepository.findById(destination.getId());
        assertTrue(foundDestination.isPresent());
    }
}
