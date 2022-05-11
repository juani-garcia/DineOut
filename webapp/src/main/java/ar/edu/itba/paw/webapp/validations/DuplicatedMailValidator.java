package ar.edu.itba.paw.webapp.validations;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class DuplicatedMailValidator implements ConstraintValidator<DuplicatedMail, String> {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public void initialize(DuplicatedMail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<Restaurant> restaurant = restaurantService.getByMail(value);
        Optional<User> user = securityService.getCurrentUser();
        return !restaurant.isPresent() ||
                (user.isPresent() && user.get().getId() == restaurant.get().getUserID());
    }
}
