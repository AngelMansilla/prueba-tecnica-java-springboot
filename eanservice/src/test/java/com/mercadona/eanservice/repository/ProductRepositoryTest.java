package com.mercadona.eanservice.repository;

import com.mercadona.eanservice.model.Product;
import com.mercadona.eanservice.model.Provider;
import com.mercadona.eanservice.model.Destination;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenSaveProduct_thenFindById() {
        Provider provider = new Provider();
        provider.setName("Proveedor Ejemplo");
        provider.setProviderCode("1234567");
        Destination destination = new Destination();
        destination.setName("Destino Ejemplo");
        destination.setCode("1");
        Product product = new Product();
        product.setEan("1234567123451");
        product.setProvider(provider);
        product.setDestination(destination);
        productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(product.getId());
        assertTrue(foundProduct.isPresent());
    }
}
