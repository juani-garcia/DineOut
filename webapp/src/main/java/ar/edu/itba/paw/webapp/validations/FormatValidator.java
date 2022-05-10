package ar.edu.itba.paw.webapp.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class FormatValidator implements ConstraintValidator<Format, String> {

    @Override
    public void initialize(Format format) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null) return false;

        try {
            LocalDate.parse(s);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }
}