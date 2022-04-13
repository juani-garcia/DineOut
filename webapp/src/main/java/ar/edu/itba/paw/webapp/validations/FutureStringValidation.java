package ar.edu.itba.paw.webapp.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FutureStringValidation implements ConstraintValidator<FutureString, String> {

    protected DateTimeFormatter formatter;

    @Override
    public void initialize(FutureString futureString) {
        this.formatter = DateTimeFormatter.ofPattern(futureString.pattern());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime dateTime;

        try {
            dateTime = LocalDateTime.parse(s, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }

        return dateTime.isAfter(LocalDateTime.now());
    }
}