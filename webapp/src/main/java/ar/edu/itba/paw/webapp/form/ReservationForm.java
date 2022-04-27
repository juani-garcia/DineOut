package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.Format;
import ar.edu.itba.paw.webapp.validations.FutureString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationForm {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm";

    @Size(max = 150)
    private String comments;

    @NotNull
    @Range(min = 1, max = 20)
    private Integer amount;

    @NotNull
    @FutureString(pattern = DATE_TIME_FORMAT)
    @Format(pattern = DATE_TIME_FORMAT)
    private String dateTime;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

}