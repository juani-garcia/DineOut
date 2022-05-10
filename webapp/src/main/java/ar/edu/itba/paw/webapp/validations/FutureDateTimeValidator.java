package ar.edu.itba.paw.webapp.validations;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class FutureDateTimeValidator implements ConstraintValidator<FutureDateTime, Object> {

    private String date;
    private String time;

    @Override
    public void initialize(FutureDateTime constraintAnnotation) {
        this.date = constraintAnnotation.date();
        this.time = constraintAnnotation.time();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalTime time = (LocalTime) new BeanWrapperImpl(value).getPropertyValue(this.time);
        LocalDate date;

        try {
            date = LocalDate.parse((String) new BeanWrapperImpl(value).getPropertyValue(this.date));
        } catch (DateTimeParseException e) {
            return false;
        }

        return LocalDateTime.of(date, time).isAfter(LocalDateTime.now());

    }
}
