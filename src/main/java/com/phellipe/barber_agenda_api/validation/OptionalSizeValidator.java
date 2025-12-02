package com.phellipe.barber_agenda_api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class OptionalSizeValidator implements ConstraintValidator<OptionalSize, Optional<String>> {

    private int min;
    private int max;

    @Override
    public void initialize(OptionalSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Optional<String> value, ConstraintValidatorContext context) {

        if (value.isEmpty()) return true;

        String text = value.get();
        return text.length() >= min && text.length() <= max;
    }
}
