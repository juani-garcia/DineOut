package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RestaurantReviewForm {

    // TODO: Add valid restaurant id
    private long restaurantId;

    @NotNull
    @Range(min = 1, max = 5)
    private Integer rating;

    @NotNull
    @Size(max = 250)
    private String review;

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReview() {
        return review;
    }
}
