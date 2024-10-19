package dev.hudsonprojects.api.common.validation.constraint.strongpassword;

import dev.hudsonprojects.api.common.lib.util.PasswordUtils;
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
