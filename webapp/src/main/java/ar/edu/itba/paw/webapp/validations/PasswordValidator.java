package ar.edu.itba.paw.webapp.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public void initialize(Password constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        boolean length = value.length() >= 8 && value.length() <= 64;

        boolean containsUpper = false, containsLower =  false, containsDigit = false;
        for(char c : value.toCharArray()) {
            if(Character.isUpperCase(c)) {
                containsUpper = true;
            } else if(Character.isDigit(c)) {
                containsDigit = true;
            } else if(Character.isLowerCase(c)) {
                containsLower = true;
            }
            if(containsDigit && containsLower && containsUpper) break;
        }

        return length && containsDigit && containsUpper && containsLower;
    }
}
