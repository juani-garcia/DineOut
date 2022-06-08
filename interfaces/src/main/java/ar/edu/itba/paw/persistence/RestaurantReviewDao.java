package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.RestaurantReview;
import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface RestaurantReviewDao {
    Optional<RestaurantReview> getById(final long reviewId);

    void delete(long reviewId);

    PagedQuery<RestaurantReview> getByRestaurantId(long page, long restaurantId);

    RestaurantReview create(String review, long rating, User user, Restaurant restaurant);
}
