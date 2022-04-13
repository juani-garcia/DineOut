package ar.edu.itba.paw.webapp.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormatValidator implements ConstraintValidator<Format, String> {

    protected DateTimeFormatter formatter;

    @Override
    public void initialize(Format format) {
        this.formatter = DateTimeFormatter.ofPattern(format.pattern());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null) return false;

        try {
            LocalDateTime.parse(s, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }
}