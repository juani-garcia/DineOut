package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.*;

@Repository
public class RestaurantReviewHibernateDao implements RestaurantReviewDao {

    private static final int PAGE_SIZE = 7;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<RestaurantReview> getById(final long reviewId) {
        return Optional.ofNullable(em.find(RestaurantReview.class, reviewId));
    }

    @Override
    public void delete(final long menuSectionId) {
        em.remove(em.find(MenuSection.class, menuSectionId));
    }

    @Override
    public PagedQuery<RestaurantReview> getByRestaurantId(long page, long restaurantId) {
        String idsQuery = "SELECT id\n" + "FROM restaurant_review WHERE restaurant_id = :restaurantId\n" + "LIMIT :limit OFFSET :offset";
        Query query = em.createNativeQuery(idsQuery);

        query.setParameter("restaurantId", restaurantId);
        query.setParameter("limit", PAGE_SIZE);
        query.setParameter("offset", PAGE_SIZE * (page - 1));

        final List<Long> ids = new ArrayList<>();
        for(Object o : query.getResultList()) {
            ids.add(((Number) o).longValue());
        }

        String countQuery = "SELECT COUNT(*)\n" + "FROM restaurant_review WHERE restaurant_id = :restaurantId";
        query = em.createNativeQuery(countQuery);
        query.setParameter("restaurantId", restaurantId);

        @SuppressWarnings("unchecked")
        long count = ((BigInteger) query.getResultList().stream().findFirst().orElse(0)).longValue();

        if (ids.isEmpty())
            return new PagedQuery<>(new ArrayList<>(), (long) page, (count+PAGE_SIZE-1)/PAGE_SIZE);

        final TypedQuery<RestaurantReview> reviews =
                em.createQuery("from RestaurantReview as r where r.id IN :ids", RestaurantReview.class);
        reviews.setParameter("ids", ids);

        return new PagedQuery<>(reviews.getResultList(), (long) page, (count+PAGE_SIZE-1)/PAGE_SIZE);

    }

    @Override
    public PagedQuery<RestaurantReview> get(long page, long pageSize, Long restaurantId, Long userId) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> args = new HashMap<>();

        sb.append("FROM restaurant_review\n");
        sb.append("WHERE true\n");
        if (restaurantId != null) {
            sb.append("AND restaurant_id = :restaurantId\n");
            args.put("restaurantId", restaurantId);
        }
        if (userId != null) {
            sb.append("AND user_id = :userId\n");
            args.put("userId", userId);
        }

        String idsQuery = "SELECT id " + sb + "LIMIT :limit OFFSET :offset";
        Query query = em.createNativeQuery(idsQuery);
        for (String key : args.keySet()) {
            query.setParameter(key, args.get(key));
        }
        query.setParameter("limit", pageSize);
        query.setParameter("offset", pageSize * (page - 1));

        final List<Long> ids = new ArrayList<>();
        for(Object o : query.getResultList()) {
            ids.add(((Number) o).longValue());
        }

        String countQuery = "SELECT COUNT(*)\n" + sb;
        query = em.createNativeQuery(countQuery);
        for (String key : args.keySet()) {
            query.setParameter(key, args.get(key));
        }

        @SuppressWarnings("unchecked")
        long count = ((Number) query.getResultList().stream().findFirst().orElse(0)).longValue();

        if (ids.isEmpty())
            return new PagedQuery<>(Collections.emptyList(), page, (count+pageSize-1)/pageSize);

        final TypedQuery<RestaurantReview> reviews =
                em.createQuery("from RestaurantReview as r where r.id IN :ids", RestaurantReview.class);
        reviews.setParameter("ids", ids);

        return new PagedQuery<>(reviews.getResultList(), page, (count+pageSize-1)/pageSize);
    }

    @Override
    public RestaurantReview create(String review, long rating, User user, Restaurant restaurant) {
        final RestaurantReview restaurantReview = new RestaurantReview(review, rating, restaurant, user);
        em.persist(restaurantReview);
        return restaurantReview;
    }

    @Override
    public boolean hasReviewedRestaurant(Long userId, Long restaurantId) {
        String isQuery = "SELECT COUNT(*)\n" + "FROM restaurant_review WHERE restaurant_id = :restaurantId AND user_id = :userId";
        Query query = em.createNativeQuery(isQuery);
        query.setParameter("restaurantId", restaurantId);
        query.setParameter("userId", userId);
        return ((BigInteger) query.getResultList().stream().findFirst().orElse(0)).longValue() > 0;
    }

}
