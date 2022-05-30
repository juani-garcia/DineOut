package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "menu_section")
public class MenuSection {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_section_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "menu_section_id_seq", name = "menu_section_id_seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private long ordering;

    @OneToMany(mappedBy = "menuSection")
    private List<MenuItem> menuItemList;

    MenuSection() {
    }

    public MenuSection(String name, Restaurant restaurant) {
        this.name = name;
        this.restaurant = restaurant;
    }

    @Deprecated
    public MenuSection(long id, String name, long restaurantId, long ordering) {
        this.id = id;
        this.name = name;
        // this.restaurantId = restaurantId;
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public MenuItem addMenuItem(final String name, final String detail, final double price, final Long imageId) {
        final MenuItem menuItem = new MenuItem(name, detail, price, this, imageId);
        this.menuItemList.add(menuItem);
        return menuItem;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuSection that = (MenuSection) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
