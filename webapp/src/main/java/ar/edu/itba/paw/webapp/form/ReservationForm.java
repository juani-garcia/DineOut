package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.Format;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class ReservationForm {

    @NotEmpty
    @Email
    private String mail;

    @Size(max = 150)
    private String comments;

    @NotNull
    @Range(min = 1, max = 20)
    private Integer amount;

    @NotNull
    @Format(pattern = "yyyy-MM-dd'T'HH:mm")
    private String dateTime;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

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

}