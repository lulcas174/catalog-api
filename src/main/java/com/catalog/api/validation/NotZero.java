package com.catalog.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotZeroValidator.class)
@Documented
public @interface NotZero {
    String message() default "O valor deve ser diferente de zero";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
