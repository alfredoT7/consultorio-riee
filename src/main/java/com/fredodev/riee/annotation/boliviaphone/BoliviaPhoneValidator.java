package com.fredodev.riee.annotation.boliviaphone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BoliviaPhoneValidator implements ConstraintValidator<BoliviaPhone, String> {
    @Override
    public void initialize(BoliviaPhone constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()) {
            return true;
        }
        String digits = value.replaceAll("\\D", "");
        if (digits.isEmpty()) {
            return false;
        }
        char first = digits.charAt(0);
        return first == '6' || first == '7';
    }


}
