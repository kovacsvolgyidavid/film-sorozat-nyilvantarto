package xyz.codingmentor.validator;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import xyz.codingmentor.constraint.NameConstraint;

public class NameConstraintValidator  implements ConstraintValidator<NameConstraint, String>{

    private Pattern pattern;
  
    private static final String NAME_PATTERN = "^([\\p{Lu}]{1}[\\p{Ll}]{1,30}[- ]{0,1}|"
            + "[\\p{Lu}]{1}[- \\']{1}[\\p{Lu}]{0,1}[\\p{Ll}]{1,30}[- ]{0,1}|[\\p{Ll}]{1,2}"
            + "[ -\\']{1}[\\p{Lu}]{1}[\\p{Ll}]{1,30}){2,5}$";
    
    @Override
    public void initialize(NameConstraint constraintAnnotation) {
        pattern = Pattern.compile(NAME_PATTERN);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if("".equals(value))
            return true;
        else
            return pattern.matcher(value).matches();
    }
}
