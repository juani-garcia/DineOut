package ar.edu.itba.paw.webapp.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeValidator implements ConstraintValidator<Time, String> {

    protected String format;

    @Override
    public void initialize(Time time) {
        this.format = time.format();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            LocalTime.parse(s, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }
}
