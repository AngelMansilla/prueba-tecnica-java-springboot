package com.mercadona.eanservice.validation;

import com.mercadona.eanservice.model.Product;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EANValidator implements ConstraintValidator<ValidEAN, Product> {

    @Override
    public void initialize(ValidEAN constraintAnnotation) {
    }

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext context) {
        if (product == null) {
            return true;
        }

        String ean = product.getEan();
        String providerCode = product.getProvider().getProviderCode();
        String destinationCode = product.getDestination().getCode();

        // Verificar que el EAN contiene el código del proveedor y el código del destino
        return ean.startsWith(providerCode) && ean.endsWith(destinationCode);
    }
}
