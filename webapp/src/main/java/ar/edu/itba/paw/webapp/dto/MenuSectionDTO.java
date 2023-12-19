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

    private Long id;
    private String name;
    private URI restaurant;
    private URI menuItemList;
    private List<URI> menuItemsOrder;
    private URI self;

    public static MenuSectionDTO fromMenuSection(final UriInfo uriInfo, final MenuSection menuSection) {
        final MenuSectionDTO dto = new MenuSectionDTO();

        dto.id = menuSection.getId();
        dto.name = menuSection.getName();
        UriBuilder restaurantUriBuilder = RestaurantDTO.getUriBuilder(uriInfo, menuSection.getRestaurant());
        dto.restaurant = restaurantUriBuilder.clone().build();
        UriBuilder menuSectionUriBuilder = MenuSectionDTO.getUriBuilder(uriInfo, menuSection);
        dto.self = menuSectionUriBuilder.clone().build();
        dto.menuItemList = MenuItemDTO.getUriBuilder(uriInfo, menuSection).build();
        dto.menuItemsOrder = menuSection.getMenuItemList().stream().map(mi -> MenuItemDTO.getUriBuilder(uriInfo, mi).build()).collect(Collectors.toList());

        return dto;
    }

    public static long getIdFromURI(URI uri) {
        String path = uri.getPath();
        String idStr = path.substring(path.lastIndexOf('/') + 1);
        return Integer.parseInt(idStr);
    }

    public static UriBuilder getUriBuilder(final UriInfo uriInfo, final Restaurant restaurant) {
        return RestaurantDTO.getUriBuilder(uriInfo, restaurant).path("menu-sections");
    }

    public static UriBuilder getUriBuilder(final UriInfo uriInfo, final MenuSection menuSection) {
        return MenuSectionDTO.getUriBuilder(uriInfo, menuSection.getRestaurant()).path(String.valueOf(menuSection.getId()));
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

    public URI getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(URI restaurant) {
        this.restaurant = restaurant;
    }

    public URI getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(URI menuItemList) {
        this.menuItemList = menuItemList;
    }

    public List<URI> getMenuItemsOrder() {
        return menuItemsOrder;
    }

    public void setMenuItemsOrder(List<URI> menuItemsOrder) {
        this.menuItemsOrder = menuItemsOrder;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

}
