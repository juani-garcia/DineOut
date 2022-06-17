package ar.edu.itba.paw.webapp.validations;

import ar.edu.itba.paw.model.Zone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidZoneValidator implements ConstraintValidator<ValidZone, String> {

    @Override
    public void initialize(ValidZone constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Zone.getByName(value) != null;
    }

}
