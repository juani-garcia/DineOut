package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.MenuItem;

import javax.ws.rs.core.UriInfo;
import java.awt.*;
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
        return null;
    }
}
