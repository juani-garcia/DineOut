package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.Category;
import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.model.Zone;
import ar.edu.itba.paw.webapp.validations.DuplicatedMail;
import ar.edu.itba.paw.webapp.validations.FileSize;
import ar.edu.itba.paw.webapp.validations.FileType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class RestaurantForm {

    @Size(min = 1, max = 100)
    @NotNull
    private String name;

    @FileSize(mb = 1)
    @FileType(types = {"image/png", "image/jpeg"})
    private MultipartFile image;

    @Size(min = 2, max = 180)
    @NotNull
    private String address;

    @Size(min = 6, max = 100)
    @Email
    @DuplicatedMail
    @NotNull
    private String email;

    @Size(max = 400)
    @NotNull
    private String detail;

    @NotNull
    private Zone zone;

    @NotNull
    @Range(min = -90, max = 90)
    private Float lat;

    @NotNull
    @Range(min = -180, max = 180)
    private Float lng;

    @NotNull
    private List<Category> categories;

    @NotNull
    @NotEmpty
    private List<Shift> shifts;

    public String getName() {
        return name;
    }

    public MultipartFile getImage() {
        return image;
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

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }
}