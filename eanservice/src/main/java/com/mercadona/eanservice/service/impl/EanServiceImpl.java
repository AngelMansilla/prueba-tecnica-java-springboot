package com.mercadona.eanservice.service.impl;

import com.mercadona.eanservice.dto.ProductDTO;
import com.mercadona.eanservice.repository.ProductRepository;
import com.mercadona.eanservice.service.EanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EanServiceImpl implements EanService {

    private final ProductRepository productRepository;

    
    public EanServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductDTO> findByEan(String ean) {
        return productRepository.findByEan(ean).map(ProductDTO::fromProduct);
    }
}
