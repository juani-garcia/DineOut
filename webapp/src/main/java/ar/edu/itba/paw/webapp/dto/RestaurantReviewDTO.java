package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.*;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Set;

public class RestaurantReviewDTO {
    private Long id;
    private String review;
    private long rating;
    private URI restaurant;
    private URI user;

    private URI self;
    public static RestaurantReviewDTO fromRestaurantReview(final UriInfo uriInfo, final RestaurantReview restaurantReview) {
        final RestaurantReviewDTO dto = new RestaurantReviewDTO();

        dto.id = restaurantReview.getId();
        dto.review = restaurantReview.getReview();
        dto.rating = restaurantReview.getRating();
        // TODO: dto.restaurant
        dto.self = RestaurantReviewDTO.getUriBuilder(uriInfo, restaurantReview).build();

        return dto;
    }

    public static UriBuilder getUriBuilder(final UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("reviews");
    }

    public static UriBuilder getUriBuilder(final UriInfo uriInfo, final RestaurantReview restaurantReview) {
        return RestaurantReviewDTO.getUriBuilder(uriInfo)
                .path(String.valueOf(restaurantReview.getId()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public URI getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(URI restaurant) {
        this.restaurant = restaurant;
    }

    public URI getUser() {
        return user;
    }

    public void setUser(URI user) {
        this.user = user;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }
}
