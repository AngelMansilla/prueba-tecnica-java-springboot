package com.mercadona.eanservice.service;

import com.mercadona.eanservice.dto.ProductDTO;
import com.mercadona.eanservice.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();

    Optional<ProductDTO> findById(Long id);

    Optional<Product> findByIdRaw(Long id);

    Product save(Product product);

    void deleteById(Long id);
}
