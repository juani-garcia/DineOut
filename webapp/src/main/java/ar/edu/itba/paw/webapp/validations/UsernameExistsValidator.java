package ar.edu.itba.paw.webapp.validations;

import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameExistsValidator implements ConstraintValidator<UsernameExists, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UsernameExists constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userService.getByUsername(value).isPresent();
    }
}
