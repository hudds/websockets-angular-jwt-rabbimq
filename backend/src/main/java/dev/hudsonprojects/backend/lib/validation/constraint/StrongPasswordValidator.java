package dev.hudsonprojects.backend.lib.validation.constraint;

import dev.hudsonprojects.backend.lib.util.PasswordUtils;
import dev.hudsonprojects.backend.lib.validation.constraint.annotation.StrongPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String>{


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null){
            return true;
        }
        return PasswordUtils.isStrong(value);
    }

}
