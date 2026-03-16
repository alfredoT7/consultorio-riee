package com.fredodev.riee.annotation.boliviaphone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BoliviaPhoneValidator implements ConstraintValidator<BoliviaPhone, Object> {
    @Override
    public void initialize(BoliviaPhone constraintAnnotation) {}

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String rawValue;
        if (value instanceof Number) {
            rawValue = String.valueOf(value);
        } else if (value instanceof CharSequence) {
            rawValue = value.toString();
            if (rawValue.isBlank()) {
                return true;
            }
        } else {
            return false;
        }

        String digits = rawValue.replaceAll("\\D", "");
        if (digits.isEmpty()) {
            return false;
        }

        char first = digits.charAt(0);
        return first == '6' || first == '7';
    }
}
