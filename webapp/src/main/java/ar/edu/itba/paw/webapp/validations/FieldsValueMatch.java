package ar.edu.itba.paw.webapp.validations;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsValueMatch {

    String message() default "{FieldsValueMatch.user.default}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field();

    String fieldMatch();

    @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsValueMatch[] value();
    }

}