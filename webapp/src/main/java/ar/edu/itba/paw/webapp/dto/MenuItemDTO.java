package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.MenuItem;
import ar.edu.itba.paw.model.MenuSection;
import org.glassfish.jersey.server.Uri;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.awt.*;
import java.net.URI;

public class MenuItemDTO {

    private Long id;
    private String name;
    private String detail;
    private double price;
    private URI menuSection;
    private URI image;
    private URI self;

    public static MenuItemDTO fromMenuItem(final UriInfo uriInfo, final MenuItem menuItem) {
        final MenuItemDTO dto = new MenuItemDTO();

        dto.id = menuItem.getId();
        dto.name = menuItem.getName();
        dto.detail = menuItem.getDetail();
        dto.price = menuItem.getPrice();
        dto.menuSection = MenuSectionDTO.getUriBuilder(uriInfo, menuItem.getSection()).build();
        final UriBuilder menuItemUriBuilder = MenuItemDTO.getUriBuilder(uriInfo, menuItem);
        if (menuItem.getImage() != null)
            dto.image = MenuItemDTO.getUriBuilderForImage(uriInfo, menuItem).build();
        dto.self = menuItemUriBuilder.clone().build();

        return dto;
    }

    public static long getIdFromURI(URI uri) {
        if (uri == null)
            throw new IllegalArgumentException("Cannot get id from null URI");
        String path = uri.getPath();
        String idStr = path.substring(path.lastIndexOf('/') + 1);
        return Integer.parseInt(idStr);
    }

    public static UriBuilder getUriBuilder(final UriInfo uriInfo, final MenuSection menuSection) {
        return MenuSectionDTO.getUriBuilder(uriInfo, menuSection).path("menu-items");
    }

    public static UriBuilder getUriBuilder(final UriInfo uriInfo, final MenuItem menuItem) {
        return MenuItemDTO.getUriBuilder(uriInfo, menuItem.getSection()).path(String.valueOf(menuItem.getId()));
    }

    public static UriBuilder getUriBuilderForImage(final UriInfo uriInfo, final MenuItem menuItem) {
        return MenuItemDTO.getUriBuilder(uriInfo, menuItem).path("image");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
