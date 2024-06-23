package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.model.Destination;
import com.mercadona.eanservice.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @GetMapping
    public List<Destination> getAllDestinations() {
        return destinationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Long id) {
        Optional<Destination> destination = destinationService.findById(id);
        return destination.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Destination createDestination(@RequestBody Destination destination) {
        return destinationService.save(destination);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destination> updateDestination(@PathVariable Long id, @RequestBody Destination destinationDetails) {
        Optional<Destination> destination = destinationService.findById(id);
        if (destination.isPresent()) {
            Destination updatedDestination = destination.get();
            updatedDestination.setName(destinationDetails.getName());
            updatedDestination.setCode(destinationDetails.getCode());
            return ResponseEntity.ok(destinationService.save(updatedDestination));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        destinationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
