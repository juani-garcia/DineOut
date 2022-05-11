package ar.edu.itba.paw.webapp.validations;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;

public class ValidImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    @Override
    public void initialize(ValidImage constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null || value.getSize() == 0) return true;
        try {
            value.getBytes();
        } catch (IOException e) {
            return false;
        }
        return value.getSize() <= 1024*1024;
    }
}
