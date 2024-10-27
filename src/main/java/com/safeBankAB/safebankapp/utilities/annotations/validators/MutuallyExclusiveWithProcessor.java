package com.safeBankAB.safebankapp.utilities.annotations.validators;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes(
        "com.safeBankAB.safebankapp.utilities.annotations.MutuallyExclusiveWith")
public class MutuallyExclusiveWithProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }
}
