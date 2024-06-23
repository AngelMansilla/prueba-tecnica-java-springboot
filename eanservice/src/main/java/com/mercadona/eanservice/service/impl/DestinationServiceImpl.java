package com.mercadona.eanservice.service.impl;

import com.mercadona.eanservice.exception.DestinationNotFoundException;
import com.mercadona.eanservice.model.Destination;
import com.mercadona.eanservice.repository.DestinationRepository;
import com.mercadona.eanservice.service.DestinationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationServiceImpl(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @Override
    public List<Destination> findAll() {
        return destinationRepository.findAll();
    }

    @Override
    public Optional<Destination> findById(Long id) {
        return Optional.ofNullable(destinationRepository.findById(id).orElseThrow(() -> new DestinationNotFoundException("Destination not found")));
    }

    @Override
    public Destination save(Destination destination) {
        return destinationRepository.save(destination);
    }

    @Override
    public void deleteById(Long id) {
        destinationRepository.deleteById(id);
    }
}
