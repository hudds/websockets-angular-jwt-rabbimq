package dev.hudsonprojects.backend.lib.validation.constraint.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.hudsonprojects.backend.lib.validation.constraint.StrongPasswordValidator;
import jakarta.validation.Constraint;

@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ ElementType.FIELD , ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrongPassword {

    String message() default "{validation.StrongPassword}";
    Class<?>[] groups() default {};
}
