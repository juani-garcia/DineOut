package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MenuItemForm {

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @Size(max = 100)
    private String detail;

    @NotNull
    private double price;

    @NotNull
    private long menuSectionId;

    @NotNull
    @Min(1)
    private int ordering;

    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getMenuSectionId() {
        return menuSectionId;
    }

    public void setMenuSectionId(long menuSectionId) {
        this.menuSectionId = menuSectionId;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
