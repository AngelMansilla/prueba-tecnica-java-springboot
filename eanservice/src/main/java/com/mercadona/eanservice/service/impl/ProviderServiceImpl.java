package com.mercadona.eanservice.service.impl;

import com.mercadona.eanservice.exception.ProviderNotFoundException;
import com.mercadona.eanservice.model.Provider;
import com.mercadona.eanservice.repository.ProviderRepository;
import com.mercadona.eanservice.service.ProviderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public List<Provider> findAll() {
        return providerRepository.findAll();
    }

    @Override
    public Optional<Provider> findById(Long id) {
        return Optional.ofNullable(providerRepository.findById(id).orElseThrow(() -> new ProviderNotFoundException("Provider not found")));
    }

    @Override
    public Provider save(Provider provider) {
        return providerRepository.save(provider);
    }

    @Override
    public void deleteById(Long id) {
        providerRepository.deleteById(id);
    }
}
