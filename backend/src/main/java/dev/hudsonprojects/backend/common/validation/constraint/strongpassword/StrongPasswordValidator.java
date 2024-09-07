package dev.hudsonprojects.backend.common.validation.constraint.strongpassword;

import dev.hudsonprojects.backend.common.lib.util.PasswordUtils;
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
