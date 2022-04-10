package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.Date;
import ar.edu.itba.paw.webapp.validations.Time;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ReservationForm {

    @NotEmpty
    @Email
    private String mail;

    @Size(max = 150)
    private String comments;

    @NotNull
    @Range(min = 1, max = 20)
    private Integer amount;

    @Date(format = "dd/MM/yyyy")
    @NotEmpty
    private String date;

    @Time(format = "HH:mm")
    @NotEmpty
    private String time;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}