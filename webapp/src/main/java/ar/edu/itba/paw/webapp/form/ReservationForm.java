package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.DateFormat;
import ar.edu.itba.paw.webapp.validations.FutureDateTime;
import ar.edu.itba.paw.webapp.validations.TimeFormat;
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

    private Long restaurantId;

    @Size(max = 150)
    private String comments;

    @NotNull
    @Range(min = 1, max = 20)
    private Integer amount;

    @NotNull
    @DateFormat(format = "yyyy-MM-dd")
    private String date;

    @NotNull
    @TimeFormat(format = "HH:mm")
    private String time;

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
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
        return LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));
    }

}