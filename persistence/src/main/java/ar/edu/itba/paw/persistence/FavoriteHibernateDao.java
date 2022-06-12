package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FavoriteHibernateDao implements FavoriteDao {

    private static final int PAGE_SIZE = 7;

    @PersistenceContext
    public EntityManager em;

    @Override
    public Favorite create(User user, Restaurant restaurant) {
        final Favorite favorite = new Favorite(restaurant, user);
        em.persist(favorite);
        return favorite;
    }

    @Override
    public void delete(User user, Restaurant restaurant) {
        final TypedQuery<Favorite> query = this.em.createQuery("from Favorite as f where f.restaurant = :restaurant and f.user = :user", Favorite.class)
                .setParameter("user", user)
                .setParameter("restaurant", restaurant);
        em.remove(query.getResultList().stream().findFirst().orElse(null));
    }

    @Override
    public boolean isFavorite(long restaurantId, long userId) {
        String isQuery = "SELECT COUNT(*)\n" + "FROM favorite WHERE restaurant_id = :restaurantId AND user_id = :userId";
        Query query = em.createNativeQuery(isQuery);
        query.setParameter("restaurantId", restaurantId);
        query.setParameter("userId", userId);
        return ((BigInteger) query.getResultList().stream().findFirst().orElse(0)).longValue() > 0;
    }

    @Override
    public PagedQuery<Favorite> getAllByUserId(long userId, int page) {
        String idsQuery = "SELECT *\n" + "FROM favorite WHERE user_id = :userId\n" + "LIMIT :limit OFFSET :offset";
        Query query = em.createNativeQuery(idsQuery, Favorite.class);

        query.setParameter("userId", userId);
        query.setParameter("limit", PAGE_SIZE);
        query.setParameter("offset", PAGE_SIZE * (page - 1));

        final List<Favorite> favoriteList = new ArrayList<>();
        for (Object o :query.getResultList()) {
            favoriteList.add((Favorite) o);
        }

        String countQuery = "SELECT COUNT(*)\n" + "FROM favorite WHERE user_id = :userId";
        query = em.createNativeQuery(countQuery);
        query.setParameter("userId", userId);

        @SuppressWarnings("unchecked")
        long count = ((BigInteger) query.getResultList().stream().findFirst().orElse(0)).longValue();

        if (favoriteList.isEmpty())
            return new PagedQuery<>(new ArrayList<>(), (long) page, (count+PAGE_SIZE-1)/PAGE_SIZE);

        final TypedQuery<Favorite> favorites =
                em.createQuery("from Favorite as f where f IN :favoriteList", Favorite.class);
        favorites.setParameter("favoriteList", favoriteList);

        return new PagedQuery<>(favorites.getResultList(), (long) page, (count+PAGE_SIZE-1)/PAGE_SIZE);

    }
}
