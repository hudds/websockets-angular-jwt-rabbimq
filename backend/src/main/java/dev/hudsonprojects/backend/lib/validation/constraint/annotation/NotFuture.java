package dev.hudsonprojects.backend.lib.validation.constraint.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.hudsonprojects.backend.lib.validation.constraint.NotFutureValidator;
import jakarta.validation.Constraint;

@Constraint(validatedBy = NotFutureValidator.class)
@Target({ ElementType.FIELD , ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotFuture {

    String message() default "{validation.NotFuture}";
    Class<?>[] groups() default {};
}
