package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "restaurant_id_seq", name = "restaurant_id_seq")
    private Long id;


    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)  // TODO: cascade = CascadeType.REMOVE?
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

}
