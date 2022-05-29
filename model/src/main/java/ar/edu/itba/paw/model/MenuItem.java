package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_item_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "menu_item_id_seq", name = "menu_item_id_seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String detail;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private MenuSection menuSection;

    @Column(nullable = false)
    private long ordering;

    // TODO: Check if id or model
    private Long imageId;

    MenuItem() {
    }

    public MenuItem(String name, String detail, double price, MenuSection menuSection, long ordering, Long imageId) {
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.menuSection = menuSection;
        this.ordering = ordering;
        this.imageId = imageId;
    }

    @Deprecated
    public MenuItem(long id, String name, String detail, double price, long sectionId, long ordering, Long imageId) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.price = price;
        // this.sectionId = sectionId;
        this.ordering = ordering;
        this.imageId = imageId;
    }

    public long getOrdering() {
        return ordering;
    }

    public void setOrdering(long ordering) {
        this.ordering = ordering;
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

    public MenuSection getSection() {
        return menuSection;
    }

    public void setSection(MenuSection menuSection) {
        this.menuSection = menuSection;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return name + ' ' + detail + " $" + price;
    }
}
