package com.mercadona.eanservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenProductIsValid_thenNoConstraintViolations() {
        Product product = new Product();
        product.setEan("1234567123451");

        Provider provider = new Provider();
        provider.setName("Proveedor Ejemplo");
        provider.setProviderCode("1234567");
        product.setProvider(provider);

        Destination destination = new Destination();
        destination.setName("Destino Ejemplo");
        destination.setCode("1");
        product.setDestination(destination);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void whenEanIsInvalid_thenOneConstraintViolation() {
        Product product = new Product();
        product.setEan("1234512345671"); 

        Provider provider = new Provider();
        provider.setName("Proveedor Ejemplo");
        provider.setProviderCode("1234567");
        product.setProvider(provider);

        Destination destination = new Destination();
        destination.setName("Destino Ejemplo");
        destination.setCode("1");
        product.setDestination(destination);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals(1, violations.size());
    }

    @Test
    public void whenEanDoesNotMatchProviderAndDestination_thenOneConstraintViolation() {
        Product product = new Product();
        product.setEan("1111111111112");
        
        Provider provider = new Provider();
        provider.setName("Proveedor Ejemplo");
        provider.setProviderCode("1234567");
        product.setProvider(provider);

        Destination destination = new Destination();
        destination.setName("Destino Ejemplo");
        destination.setCode("1");
        product.setDestination(destination);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals(1, violations.size());
    }
}
