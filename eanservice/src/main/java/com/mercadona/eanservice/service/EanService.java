package com.mercadona.eanservice.service;

import com.mercadona.eanservice.dto.ProductDTO;

import java.util.Optional;

public interface EanService {
    Optional<ProductDTO> findByEan(String ean);
    String determineDestination(String ean);
}
