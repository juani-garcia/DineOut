package ar.edu.itba.paw.webapp.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalTime;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Constraint(validatedBy = FutureDateTimeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureDateTime {

    String message() default "Date and time is in the past";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String date();

    String time();

    @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FutureDateTime[] value();
    }

}