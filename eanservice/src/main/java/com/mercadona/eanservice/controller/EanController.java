package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.dto.ProductDTO;
import com.mercadona.eanservice.service.EanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/ean")
public class EanController {

    private final EanService eanService;

    public EanController(EanService eanService) {
        this.eanService = eanService;
    }

    @GetMapping("/{ean}")
    public ResponseEntity<ProductDTO> getProductByEan(@PathVariable String ean) {
        Optional<ProductDTO> productDTO = eanService.findByEan(ean);
        return productDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
