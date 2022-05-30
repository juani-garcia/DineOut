package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "restaurant_id_seq", name = "restaurant_id_seq")
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY, orphanRemoval = true)  // TODO: cascade = CascadeType.REMOVE?
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "image_id")
    private Long imageId;


//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "favorite",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
//    private Long favCount;

    @Column(name = "name")
    private String name;

    @Column(name = "mail")
    private String mail;

    @Column(name = "address")
    private String address;

    @Column(name = "detail")
    private String detail;

    @Column(name = "zone_id", columnDefinition = "Zone")
    @Enumerated(EnumType.ORDINAL)
    private Zone zone;

    // TODO: Correct
    @ManyToMany(targetEntity = Shift.class, fetch = FetchType.LAZY)
    @JoinTable(name = "restaurant_shift",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "opening_hours_id"))
    @Enumerated(EnumType.ORDINAL)
    private Set<Shift> shifts;

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "restaurant_category",
            joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "category_id")
    @Enumerated(EnumType.ORDINAL)
    private Set<Category> categories;
    
    @OneToMany(mappedBy = "restaurant")
    private List<MenuSection> menuSectionList;

    protected Restaurant() {
    }

    public Restaurant(long id, User user, String name, Long imageId, String address, String mail, String detail, Zone zone, Long favCount) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.imageId = imageId;
        this.address = address;
        this.detail = detail;
        this.mail = mail;
        this.zone = zone;
    }

    @Deprecated
    public Restaurant(long id, Long userID, String name, Long imageId, String address, String mail, String detail, Zone zone, Long favCount) {
        this.id = id;
        // this.userID = userID;
        this.name = name;
        this.imageId = imageId;
        this.address = address;
        this.detail = detail;
        this.mail = mail;
        this.zone = zone;
        // this.favCount = favCount;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getImageId() {
        return imageId;
    }

    public String getAddress() {
        return address;
    }

    public String getDetail() {
        return detail;
    }

    public Zone getZone() {
        return zone;
    }

    public User getUser() {
        return user;
    }

    public String getMail() {
        return mail;
    }

    public Set<Shift> getShifts() {
        return shifts;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public List<MenuSection> getMenuSectionList() {
        return menuSectionList;
    }

    public MenuSection addMenuSection(final String name) {
        final MenuSection menuSection = new MenuSection(name, this);
        this.menuSectionList.add(menuSection);
        return menuSection;
    }

    public void setShifts(Collection<Shift> shifts) {
        this.shifts.clear();
        this.shifts.addAll(shifts);
    }

    public void setCategories(final Collection<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
