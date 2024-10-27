package com.safeBankAB.safebankapp.utilities.annotations;

import com.safeBankAB.safebankapp.utilities.annotations.validators.MutuallyExclusiveWithValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MutuallyExclusiveWithValidator.class)
@Documented
public @interface MutuallyExclusiveWith {
    String message() default "test";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
