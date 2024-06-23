package com.mercadona.eanservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EANValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEAN {
    String message() default "EAN no coincide con el código del proveedor y el destino";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
