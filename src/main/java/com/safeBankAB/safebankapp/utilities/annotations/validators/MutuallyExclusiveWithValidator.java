package com.safeBankAB.safebankapp.utilities.annotations.validators;

import com.safeBankAB.safebankapp.utilities.annotations.MutuallyExclusiveWith;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class MutuallyExclusiveWithValidator implements ConstraintValidator<MutuallyExclusiveWith, Object> {
    @Override
    public void initialize(MutuallyExclusiveWith constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null || !value.getClass().equals(Optional.class)) {
            return false;
        }
        return true;
    }
}
