package ar.edu.itba.paw.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "restaurant_id_seq", name = "restaurant_id_seq")
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY, orphanRemoval = true)  // TODO: cascade = CascadeType.REMOVE?
    @JoinColumn(name = "user_id")
    private User user;

    public Long getFavCount() {
        return favCount;
    }

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;


    @Formula(value = "(SELECT COUNT(*) FROM favorite WHERE favorite.restaurant_id = id)")
    private Long favCount;

    @Column(name = "name")
    private String name;

    @Column(name = "mail")
    private String mail;

    @Column(name = "address")
    private String address;

    @Column(name = "detail")
    private String detail;

    @Column(name = "zone_id")
    @Enumerated(EnumType.ORDINAL)
    private Zone zone;

    @ElementCollection(targetClass = Shift.class)
    @CollectionTable(name = "restaurant_opening_hours",
            joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "opening_hours_id")
    @Enumerated(EnumType.ORDINAL)
    private Set<Shift> shifts = new HashSet<>();

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "restaurant_category",
            joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "category_id")
    @Enumerated(EnumType.ORDINAL)
    private Set<Category> categories = new HashSet<>();
    
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @OrderColumn(name = "ordering")
    private List<MenuSection> menuSectionList = new ArrayList<>();

    protected Restaurant() {
    }

    public Restaurant(User user, String name, Image image, String address, String mail, String detail, Zone zone) {
        this.name = name;
        this.user = user;
        this.image = image;
        this.address = address;
        this.detail = detail;
        this.mail = mail;
        this.zone = zone;
    }

    @Deprecated
    /* Only for testing purposes */
    public Restaurant(long id, User user, String name, Long imageId, String address, String mail, String detail, Zone zone) {
        this.id = id;
        this.user = user;
        this.name = name;
        // this.imageId = imageId;
        this.address = address;
        this.detail = detail;
        this.mail = mail;
        this.zone = zone;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setShifts(Collection<Shift> shifts) {
        this.shifts.clear();
        this.shifts.addAll(shifts);
    }

    public void setCategories(final Collection<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
    }

    public MenuSection addMenuSection(final String name) {
        final MenuSection menuSection = new MenuSection(name, this);
        this.menuSectionList.add(menuSection);
        return menuSection;
    }

    public RestaurantReview addReview(final String review, final long rating, User user) {
        System.out.println(this.getId());
        System.out.println(this.getId());
        System.out.println(this.getId());
        System.out.println(this.getId());
        RestaurantReview resrev = new RestaurantReview(review, rating, this, user); // TODO: should we remove this method or keep it for "consistency" (addMenuSection).

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA AYUDA AAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(resrev.getId());
        System.out.println(resrev.getId());
        System.out.println(resrev.getId());
        System.out.println(resrev.getUser());
        System.out.println(resrev.getUser());
        System.out.println(resrev.getUser());
        System.out.println(resrev.getReview());
        System.out.println(resrev.getReview());
        System.out.println(resrev.getReview());
        System.out.println(resrev.getReview());

        return resrev;
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
