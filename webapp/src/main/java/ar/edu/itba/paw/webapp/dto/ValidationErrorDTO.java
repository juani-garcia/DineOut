package ar.edu.itba.paw.webapp.dto;

import javax.validation.ConstraintViolation;

public class ValidationErrorDTO {

    private String message;
    private String path;

    public static ValidationErrorDTO fromValidationException(final ConstraintViolation<?> violation) {
        final ValidationErrorDTO dto = new ValidationErrorDTO();
        dto.message = violation.getMessage();
        dto.path = violation.getPropertyPath().toString();
        return dto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
