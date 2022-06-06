package ar.edu.itba.paw.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class RestaurantReviewHibernateDaoTest {

    @Autowired
    private RestaurantReviewDao restaurantReviewDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void passThrough() {

    }

//    @Test  // TODO
//    public void testGetByRestaurantId() {
//        RestaurantReview restaurantReview = new RestaurantReview(, password, firstName, lastName);
//        em.persist(restaurantReview);
//    }
}
