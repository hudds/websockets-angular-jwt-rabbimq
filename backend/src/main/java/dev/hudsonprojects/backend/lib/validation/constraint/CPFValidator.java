package dev.hudsonprojects.backend.lib.validation.constraint;

import dev.hudsonprojects.backend.lib.validation.constraint.annotation.CPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return true;
    }

}
