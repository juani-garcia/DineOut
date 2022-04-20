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

public class RestaurantForm {
    @Size(min = 1, max = 100)
    @NotNull
    private String name;

    @Size(min = 2, max = 180)
    @NotNull
    private String address;

    @Size(min = 6, max = 100)
    @Email
    @NotNull
    private String email;

    @Size(max=400)
    @NotNull
    private String detail;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getDetail() {
        return detail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}