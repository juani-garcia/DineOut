package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.RestaurantReview;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.persistence.RestaurantReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RestaurantReviewServiceImpl implements RestaurantReviewService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantReviewDao restaurantReviewDao;

    @Override
    public Optional<RestaurantReview> getById(long reviewId) {
        return restaurantReviewDao.getById(reviewId);
    }

    @Override
    public PagedQuery<RestaurantReview> getByRestaurantId(long page, long restaurantId) {
        return restaurantReviewDao.getByRestaurantId(page, restaurantId);
    }

    @Transactional
    @Override
    public RestaurantReview create(String review, long rating, long restaurantId) {
        if (rating>5) rating = 5;
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        Restaurant restaurant = restaurantService.getById(restaurantId).orElseThrow(NotFoundException::new);
        if (restaurant.getUser().equals(user))
            throw new IllegalArgumentException("Cannot review a restaurant if you are a restaurant");
        return restaurantReviewDao.create(review, rating, user, restaurant);
    }

    @Override
    @Transactional
    public void delete(long reviewId) {
        RestaurantReview restaurantReview = validateReview(reviewId);
        restaurantReviewDao.delete(reviewId);
    }

    @Override
    @Transactional
    public RestaurantReview edit(long reviewId, String review, long rating) {
        RestaurantReview restaurantReview = validateReview(reviewId);

        restaurantReview.setReview(review);
        restaurantReview.setRating(rating);

        return restaurantReview;
    }

    private RestaurantReview validateReview(final long reviewId) {
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        RestaurantReview restaurantReview = getById(reviewId).orElseThrow(IllegalArgumentException::new);
        if (!user.equals(restaurantReview.getUser()))
            throw new IllegalArgumentException("Cannot modify someone else's review");
        return restaurantReview;
    }

}
