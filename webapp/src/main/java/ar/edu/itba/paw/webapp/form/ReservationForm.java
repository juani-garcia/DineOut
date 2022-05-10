package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.Format;
import ar.edu.itba.paw.webapp.validations.FutureDateTime;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@FutureDateTime(
        date = "date",
        time = "time"
)
public class ReservationForm {

    @Size(max = 150)
    private String comments;

    @NotNull
    @Range(min = 1, max = 20)
    private Integer amount;

    @NotNull
    @Format
    private String date;

    @NotNull
    private LocalTime time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.of(LocalDate.parse(date), time);
    }

}