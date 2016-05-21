package xyz.codingmentor.validator;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import xyz.codingmentor.constraint.UsernameConstraint;

public class UsernameConstraintValidator implements ConstraintValidator<UsernameConstraint, String> {

    private Pattern pattern;

    private static final String USERNAME_PATTERN = "^[\\p{Ll}0-9_-]{3,15}$";

    @Override
    public void initialize(UsernameConstraint constraintAnnotation) {
        pattern = Pattern.compile(USERNAME_PATTERN);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ("".equals(value)) {
            return true;
        } else {
            return pattern.matcher(value).matches();
        }
    }
}
