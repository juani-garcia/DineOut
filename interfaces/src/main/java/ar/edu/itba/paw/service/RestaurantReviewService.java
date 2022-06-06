package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.RestaurantReview;

import java.util.Optional;

public interface RestaurantReviewService {
    Optional<RestaurantReview> getById(final long reviewId);

    PagedQuery<RestaurantReview> getByRestaurantId(long page, long restaurantId);

    RestaurantReview create(String review, long rating, long restaurantId);

    void delete(long reviewId);

    RestaurantReview edit(long reviewId, String review, long rating);
}
