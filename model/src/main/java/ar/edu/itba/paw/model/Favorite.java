package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "favorite")
public class Favorite {

    @PrePersist
    private void prePersist() {
        if (this.id == null) {
            this.id = new FavoriteId(this.restaurant.getId(), this.user.getId());
        }
    }

    @EmbeddedId
    private FavoriteId id;

    @ManyToOne(optional = false)
    @MapsId("restaurant_id")
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId("user_id")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Favorite() {
    }

    public Favorite(Restaurant restaurant, User user) {
        this.restaurant = restaurant;
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(getRestaurant(), favorite.getRestaurant()) && Objects.equals(getUser(), favorite.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRestaurant(), getUser());
    }
}
