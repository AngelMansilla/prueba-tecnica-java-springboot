package com.mercadona.eanservice.service;

import com.mercadona.eanservice.model.Destination;
import java.util.List;
import java.util.Optional;

public interface DestinationService {
    List<Destination> findAll();
    Optional<Destination> findById(Long id);
    Destination save(Destination destination);
    void deleteById(Long id);
}
