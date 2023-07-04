package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.MenuSection;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

public class MenuSectionDTO {

    private String name;
    private URI restaurant;
    private long ordering;
    private List<URI> menuItemList;
    private URI self;

    public static MenuSectionDTO fromMenuSection(final UriInfo uriInfo, final MenuSection menuSection) {
        final MenuSectionDTO dto = new MenuSectionDTO();

        dto.name = menuSection.getName();
        UriBuilder restaurantUriBuilder = uriInfo.getAbsolutePathBuilder().replacePath("restaurants").path(String.valueOf(menuSection.getRestaurant().getId()));
        dto.restaurant = restaurantUriBuilder.clone().build();
        dto.ordering = menuSection.getOrdering();
        dto.self = restaurantUriBuilder.clone().path("menu-sections").path(String.valueOf(menuSection.getId()).build();
        // TODO: menuItemList

        return dto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(URI restaurant) {
        this.restaurant = restaurant;
    }

    public long getOrdering() {
        return ordering;
    }

    public void setOrdering(long ordering) {
        this.ordering = ordering;
    }

    public List<URI> getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(List<URI> menuItemList) {
        this.menuItemList = menuItemList;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

}
