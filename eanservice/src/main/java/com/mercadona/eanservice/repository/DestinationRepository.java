package com.mercadona.eanservice.repository;

import com.mercadona.eanservice.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
}
