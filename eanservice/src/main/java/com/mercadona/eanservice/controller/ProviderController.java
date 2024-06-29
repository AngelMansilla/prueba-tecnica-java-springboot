package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.model.Provider;
import com.mercadona.eanservice.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @GetMapping
    public List<Provider> getAllProviders() {
        return providerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Provider> getProviderById(@PathVariable Long id) {
        Optional<Provider> provider = providerService.findById(id);
        return provider.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Provider createProvider(@RequestBody Provider provider) {
        return providerService.save(provider);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Provider> updateProvider(@PathVariable Long id, @RequestBody Provider providerDetails) {
        Optional<Provider> provider = providerService.findById(id);
        if (provider.isPresent()) {
            Provider updatedProvider = provider.get();
            updatedProvider.setName(providerDetails.getName());
            updatedProvider.setProviderCode(providerDetails.getProviderCode());
            return ResponseEntity.ok(providerService.save(updatedProvider));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
