package ar.edu.itba.paw.webapp.validations;

import ar.edu.itba.paw.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidRestaurantIdValidator implements ConstraintValidator<ValidRestaurantId, Long> {

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value != null && restaurantService.getById(value).isPresent();
    }

}
