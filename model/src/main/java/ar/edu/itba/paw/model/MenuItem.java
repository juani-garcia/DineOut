package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "menu_item",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"section_id", "ordering"}) })
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "section_id", nullable = false)
    private MenuSection menuSection;

    @Column(nullable = false)
    private long ordering;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    MenuItem() {
    }

    public MenuItem(String name, String detail, double price, MenuSection menuSection, Image image) {
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.menuSection = menuSection;
        this.ordering = menuSection.getMenuItemList().size();
        this.image = image;
    }

    @Deprecated
    /* Only for testing purposes */
    public MenuItem(long id, String name, String detail, double price, MenuSection section, long ordering, Image image) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.menuSection = section;
        this.ordering = ordering;
        this.image = image;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return name + ' ' + detail + " $" + price;
    }
}
