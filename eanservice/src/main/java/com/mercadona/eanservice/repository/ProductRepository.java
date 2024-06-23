package com.mercadona.eanservice.repository;

import com.mercadona.eanservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
