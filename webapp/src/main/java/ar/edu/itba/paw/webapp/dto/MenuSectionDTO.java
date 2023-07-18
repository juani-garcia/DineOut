package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.MenuItem;
import ar.edu.itba.paw.model.MenuSection;
import ar.edu.itba.paw.model.Restaurant;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class MenuSectionDTO {

    private String name;
    private URI restaurant;
    private long ordering;
    private URI menuItemList;
    private URI self;

    public static MenuSectionDTO fromMenuSection(final UriInfo uriInfo, final MenuSection menuSection) {
        final MenuSectionDTO dto = new MenuSectionDTO();

        dto.name = menuSection.getName();
        UriBuilder restaurantUriBuilder = RestaurantDTO.getUriBuilder(uriInfo, menuSection.getRestaurant());
        dto.restaurant = restaurantUriBuilder.clone().build();
        dto.ordering = menuSection.getOrdering();
        UriBuilder menuSectionUriBuilder = MenuSectionDTO.getUriBuilder(uriInfo, menuSection);
        dto.self = menuSectionUriBuilder.clone().build();
        dto.menuItemList = MenuItemDTO.;

        return dto;
    }

    public static UriBuilder getUriBuilder(final UriInfo uriInfo, final Restaurant restaurant) {
        return RestaurantDTO.getUriBuilder(uriInfo, restaurant).path("menu-sections");
    }

    public static UriBuilder getUriBuilder(final UriInfo uriInfo, final MenuSection menuSection) {
        return MenuSectionDTO.getUriBuilder(uriInfo, menuSection.getRestaurant()).path(String.valueOf(menuSection.getId()));
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

    public URI getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(URI menuItemList) {
        this.menuItemList = menuItemList;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

}
