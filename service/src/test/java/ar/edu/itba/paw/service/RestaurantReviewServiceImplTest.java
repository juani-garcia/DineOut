package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.RestaurantReview;
import ar.edu.itba.paw.persistence.RestaurantReviewDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static ar.edu.itba.paw.service.TestValues.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantReviewServiceImplTest {

    @InjectMocks
    private RestaurantReviewServiceImpl restaurantReviewService;

    @Mock
    private SecurityService securityService;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private RestaurantReviewDao restaurantReviewDao;

    @Test
    public void testCreateRestaurantReview() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(AUX_USER));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));
        when(restaurantReviewDao.create(anyString(), anyLong(), any(), any())).thenReturn(RESTAURANT_REVIEW);

        RestaurantReview restaurantReview = null;
        try {
            restaurantReview = restaurantReviewService.create(RESTAURANT_REVIEW_NAME, RESTAURANT_REVIEW_RATING, RESTAURANT_ID);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create restaurantReview: " + e.getMessage());
        }

        Assert.assertNotNull(restaurantReview);
        Assert.assertEquals(RESTAURANT, restaurantReview.getRestaurant());
    }

    @Test
    public void testEditRestaurantReview() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(restaurantReviewDao.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT_REVIEW));

        RestaurantReview restaurantReview = null;
        try {
            restaurantReview = restaurantReviewService.edit(RESTAURANT_REVIEW_ID, RESTAURANT_REVIEW_NAME_2, RESTAURANT_REVIEW_RATING_2);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuItem: " + e.getMessage());
        }
        Assert.assertNotNull(restaurantReview);
        Assert.assertEquals(RESTAURANT_REVIEW_RATING_2, restaurantReview.getRating());
        Assert.assertEquals(RESTAURANT_REVIEW_NAME_2, restaurantReview.getReview());

    }


}
