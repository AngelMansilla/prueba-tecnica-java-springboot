package com.mercadona.eanservice.model;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProviderTest {

    private final Validator validator;

    public ProviderTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenProviderIsValid_thenNoConstraintViolations() {
        Provider provider = new Provider();
        provider.setName("Valid Provider");
        provider.setProviderCode("1234567");

        Set<ConstraintViolation<Provider>> violations = validator.validate(provider);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void whenNameIsNull_thenOneConstraintViolation() {
        Provider provider = new Provider();
        provider.setName(null);
        provider.setProviderCode("1234567");

        Set<ConstraintViolation<Provider>> violations = validator.validate(provider);
        assertEquals(1, violations.size());
    }

    @Test
    public void whenProviderCodeIsInvalid_thenOneConstraintViolation() {
        Provider provider = new Provider();
        provider.setName("Valid Provider");
        provider.setProviderCode("invalid_code");

        Set<ConstraintViolation<Provider>> violations = validator.validate(provider);
        assertEquals(1, violations.size());
    }
}
