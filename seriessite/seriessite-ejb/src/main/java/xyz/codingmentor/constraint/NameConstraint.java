package xyz.codingmentor.constraint;

import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import xyz.codingmentor.validator.NameConstraintValidator;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = NameConstraintValidator.class)
public @interface NameConstraint {

    String message() default "{NameConstraint.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
