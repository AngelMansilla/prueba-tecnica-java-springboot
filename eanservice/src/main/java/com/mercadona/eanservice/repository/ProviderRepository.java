package com.mercadona.eanservice.repository;

import com.mercadona.eanservice.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
