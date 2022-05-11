package ar.edu.itba.paw.persistence;

import java.util.List;

public class MenuSection {

    private long id, restaurantId, ordering;
    private String name;
    private List<MenuItem> menuItemList;

    public MenuSection(long id, String name, long restaurantId, long ordering) {
        this.id = id;
        this.name = name;
        this.restaurantId = restaurantId;
        this.ordering = ordering;
    }

    public long getOrdering() {
        return ordering;
    }

    public void setOrdering(long ordering) {
        this.ordering = ordering;
    }

    public List<MenuItem> getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return name;
    }
}
