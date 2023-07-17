package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.MenuItem;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class MenuItemDTO {

    private String name;
    private String detail;
    private double price;
    private URI menuSection;
    private long ordering;
    private URI image;
    private URI self;

    public static MenuItemDTO fromMenuItem(final UriInfo uriInfo, final MenuItem menuItem) {
        final MenuItemDTO dto = new MenuItemDTO();

        dto.name = menuItem.getName();
        dto.detail = menuItem.getDetail();
        dto.price = menuItem.getPrice();
        // TODO: Complete
        return dto;
    }

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

    public URI getMenuSection() {
        return menuSection;
    }

    public void setMenuSection(URI menuSection) {
        this.menuSection = menuSection;
    }

    public long getOrdering() {
        return ordering;
    }

    public void setOrdering(long ordering) {
        this.ordering = ordering;
    }

    public URI getImage() {
        return image;
    }

    public void setImage(URI image) {
        this.image = image;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }
}
