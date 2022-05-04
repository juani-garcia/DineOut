package ar.edu.itba.paw.webapp.validations;

import ar.edu.itba.paw.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DuplicatedMailValidator implements ConstraintValidator<DuplicatedMail, String> {

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public void initialize(DuplicatedMail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !restaurantService.getByMail(value).isPresent();
    }
}
