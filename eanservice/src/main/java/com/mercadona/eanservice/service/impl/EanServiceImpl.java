package com.mercadona.eanservice.service.impl;

import com.mercadona.eanservice.dto.ProductDTO;
import com.mercadona.eanservice.repository.ProductRepository;
import com.mercadona.eanservice.model.Destination;
import com.mercadona.eanservice.service.EanService;
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
        return productRepository.findByEan(ean).map(product -> {
            product.setDestination(new Destination(determineDestination(ean)));
            return ProductDTO.fromProduct(product);
        });
    }

    @Override
    public String determineDestination(String ean) {
        char lastDigit = ean.charAt(ean.length() - 1);
        switch (lastDigit) {
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
                return "Tiendas Mercadona España";
            case '6':
                return "Tiendas Mercadona Portugal";
            case '8':
                return "Almacenes";
            case '9':
                return "Oficinas Mercadona";
            case '0':
                return "Colmenas";
            default:
                throw new IllegalArgumentException("EAN no válido");
        }
    }
}
