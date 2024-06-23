package com.mercadona.eanservice.model;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DestinationTest {

    private final Validator validator;

    public DestinationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenDestinationIsValid_thenNoConstraintViolations() {
        Destination destination = new Destination();
        destination.setName("Valid Destination");
        destination.setCode("1");

        Set<ConstraintViolation<Destination>> violations = validator.validate(destination);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void whenNameIsNull_thenOneConstraintViolation() {
        Destination destination = new Destination();
        destination.setName(null);
        destination.setCode("1");

        Set<ConstraintViolation<Destination>> violations = validator.validate(destination);
        assertEquals(1, violations.size());
    }

    @Test
    public void whenCodeIsInvalid_thenOneConstraintViolation() {
        Destination destination = new Destination();
        destination.setName("Valid Destination");
        destination.setCode("invalid_code");

        Set<ConstraintViolation<Destination>> violations = validator.validate(destination);
        assertEquals(1, violations.size());
    }
}
