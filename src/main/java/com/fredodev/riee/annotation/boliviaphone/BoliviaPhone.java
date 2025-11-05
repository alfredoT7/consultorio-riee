package com.fredodev.riee.annotation.boliviaphone;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BoliviaPhoneValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BoliviaPhone {
    String message() default "El teléfono debe pertenecer a Bolivia y empezar con 6 o 7";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
