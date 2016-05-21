package xyz.codingmentor.constraint;

import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import xyz.codingmentor.validator.UsernameConstraintValidator;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UsernameConstraintValidator.class)
public @interface UsernameConstraint {

    String message() default "{UsernameConstraint.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
