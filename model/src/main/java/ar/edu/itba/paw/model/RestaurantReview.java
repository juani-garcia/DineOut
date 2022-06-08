package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "restaurant_review")
public class RestaurantReview {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_review_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "restaurant_review_id_seq", name = "restaurant_review_id_seq")
    private Long id;

    @Column(nullable = false)
    private String review;

    @Column(nullable = false)
    private long rating;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    RestaurantReview() {
    }

    public RestaurantReview(String review, long rating, Restaurant restaurant, User user) {
        this.review = review;
        this.rating = rating;
        this.restaurant = restaurant;
        this.user = user;
    }

    @Deprecated
    /* Only for testing purposes */
    public RestaurantReview(Long id, String review, long rating, Restaurant restaurant, User user) {
        this.id = id;
        this.review = review;
        this.rating = rating;
        this.restaurant = restaurant;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getReview() {
        return review;
    }

    public long getRating() {
        return rating;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantReview that = (RestaurantReview) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
