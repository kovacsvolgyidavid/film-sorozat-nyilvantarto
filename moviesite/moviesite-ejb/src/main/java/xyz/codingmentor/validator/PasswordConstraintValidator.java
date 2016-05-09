package xyz.codingmentor.validator;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import xyz.codingmentor.constraint.PasswordConstraint;

public class PasswordConstraintValidator implements ConstraintValidator<PasswordConstraint, String> {
    
    private Pattern pattern;
  
    private static final String PASSWORD_PATTERN = "^((?=.*\\d)(?=.*\\p{Ll})(?=.*\\p{Lu}).{6,20})$";

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if("".equals(value))
            return true;
        else
            return pattern.matcher(value).matches();
    } 
}

