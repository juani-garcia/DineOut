package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.Zone;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotNull
    private String zone;

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

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
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