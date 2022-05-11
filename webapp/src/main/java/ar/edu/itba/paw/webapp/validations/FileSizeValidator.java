package ar.edu.itba.paw.webapp.validations;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private long mb;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        mb = constraintAnnotation.mb();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null || value.getSize() == 0) return true;
        try {
            value.getBytes();
        } catch (IOException e) {
            return false;
        }
        return value.getSize() <= mb * 1024*1024;
    }
}
