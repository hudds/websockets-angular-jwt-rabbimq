package dev.hudsonprojects.backend.common.validation.constraint.notfuture;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = NotFutureValidator.class)
@Target({ ElementType.FIELD , ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotFuture {

    String message() default "{validation.NotFuture}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
