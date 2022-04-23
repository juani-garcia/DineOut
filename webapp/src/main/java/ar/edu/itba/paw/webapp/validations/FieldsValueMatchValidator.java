package ar.edu.itba.paw.webapp.validations;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;

    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    public boolean isValid(final Object value, final ConstraintValidatorContext context) {

        Object fieldValue = new BeanWrapperImpl(value)
          .getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value)
          .getPropertyValue(fieldMatch);

        boolean isValid = Objects.equals(fieldValue, fieldMatchValue);

        if (! isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("validation.matchingfields")
                    .addNode(fieldMatch).addConstraintViolation();
        }

        return isValid;
    }

}
