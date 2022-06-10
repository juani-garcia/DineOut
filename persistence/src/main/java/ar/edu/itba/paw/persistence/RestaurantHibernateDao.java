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
public class RestaurantHibernateDao implements RestaurantDao {

    private static final int PAGE_SIZE = 8;
    private static final Comparator<Restaurant> RATING_FAVORITES_COMPARATOR =
            Comparator.comparingLong(Restaurant::getRating).thenComparingLong(Restaurant::getFavCount).reversed();

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Restaurant> getById(long id) {
        return Optional.ofNullable(em.find(Restaurant.class, id));
    }

    @Override
    public Optional<Restaurant> getByMail(String mail) {
        final TypedQuery<Restaurant> query = em.createQuery("from Restaurant as r where r.mail = :mail",
                Restaurant.class).setParameter("mail", mail);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<Restaurant> getByUserId(long id) {
        final TypedQuery<Restaurant> query = em.createQuery("from Restaurant as r where r.user.id = :id",
                Restaurant.class).setParameter("id", id);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public PagedQuery<Restaurant> filter(int page, String name, boolean byItem, Category category, Shift shift, Zone zone) {

        ParametrizedQuery filter = filterBuilder(name, byItem, category, shift, zone);
        String idsQuery = "SELECT id\n" + filter.query + "ORDER BY rating DESC, fav_count DESC LIMIT :limit OFFSET :offset";
        Query query = em.createNativeQuery(idsQuery);

        for (String key : filter.args.keySet()) {
            query.setParameter(key, filter.args.get(key));
        }
        query.setParameter("limit", PAGE_SIZE);
        query.setParameter("offset", PAGE_SIZE * (page - 1));

        final List<Long> ids = new ArrayList<>();
        for (Object o : query.getResultList()) {
            ids.add(((Integer) o).longValue());
        }

        String countQuery = "SELECT COUNT(*)\n" + filter.query;
        query = em.createNativeQuery(countQuery);

        for (String key : filter.args.keySet()) {
            query.setParameter(key, filter.args.get(key));
        }

        @SuppressWarnings("unchecked")
        long count = ((BigInteger) query.getResultList().stream().findFirst().orElse(0)).longValue();

        if (ids.isEmpty())
            return new PagedQuery<>(new ArrayList<>(), (long) page, (count + PAGE_SIZE - 1) / PAGE_SIZE);

        final TypedQuery<Restaurant> restaurants =
                em.createQuery("from Restaurant as r where r.id IN :ids", Restaurant.class);
        restaurants.setParameter("ids", ids);

        List<Restaurant> content = restaurants.getResultList();
        content.sort(RATING_FAVORITES_COMPARATOR);

        return new PagedQuery<>(content, (long) page, (count + PAGE_SIZE - 1) / PAGE_SIZE);
    }

    private ParametrizedQuery filterBuilder(String name, boolean byItem, Category category, Shift shift, Zone zone) {
        StringBuilder sql = new StringBuilder();
        sql.append("FROM (SELECT restaurant.*, (SELECT COALESCE(FLOOR(AVG(restaurant_review.rating)), 0) FROM restaurant_review WHERE restaurant_review.restaurant_id = restaurant.id) rating, (SELECT COUNT(*) FROM favorite f WHERE f.restaurant_id = id) fav_count FROM restaurant ) restaurant_favCount\n");
        sql.append("WHERE true\n");

        Map<String, Object> args = new HashMap<>();

        if (name != null && !name.equals("")) {
            sql.append("AND ");
            if(byItem) {
                sql.append("id IN (\n");
                sql.append("(SELECT restaurant_id FROM menu_section WHERE menu_section.id IN (SELECT section_id FROM menu_item WHERE Lower(name) LIKE :name))\n");
                sql.append("UNION\n");
                sql.append("(SELECT restaurant_id FROM menu_section WHERE LOWER(menu_section.name) LIKE :name)\n");
                sql.append(")\n");
            } else {
                sql.append("LOWER(name) like :name\n");
            }

            args.put("name", '%' + name.toLowerCase() + '%');
        }

        if (category != null) {
            sql.append("AND id in (SELECT restaurant_id FROM restaurant_category WHERE category_id = :category)\n");
            args.put("category", category.getId());
        }

        if (shift != null) {
            sql.append("AND id in (SELECT restaurant_id FROM restaurant_opening_hours WHERE opening_hours_id = :shift)\n");
            args.put("shift", shift.getId());
        }

        if (zone != null) {
            sql.append("AND zone_id = :zone\n");
            args.put("zone", zone.getId());
        }

        return new ParametrizedQuery(sql.toString(), args);
    }

    @Override
    public Restaurant create(User user, String name, Image image, String address, String mail, String detail, Zone zone) {
        Restaurant restaurant = new Restaurant(user, name, image, address, mail, detail, zone);
        em.persist(restaurant);
        return restaurant;
    }

    @Override
    public List<Restaurant> getTopTenByFavorite() {
        String sql = "SELECT id " +
                "FROM (SELECT r.id, (SELECT count(*) FROM favorite WHERE restaurant_id = r.id) " +
                "AS fav_count, " +
                "(SELECT COALESCE(FLOOR(AVG(restaurant_review.rating)), 0) FROM " +
                "restaurant_review WHERE restaurant_review.restaurant_id = r.id) AS rating " +
                "FROM restaurant r) restaurant_favs ORDER BY restaurant_favs.rating DESC, restaurant_favs.fav_count DESC " +
                "LIMIT 10";
        return makeJPAQueryFromNative(sql, null);
    }


    @Override
    public List<Restaurant> getTopTenByReservations() {
        String sql = "SELECT id " +
                "FROM (SELECT r.id, (SELECT count(*) FROM reservation WHERE restaurant_id = r.id) " +
                "AS reservation_count FROM restaurant r) restaurant_reservation " +
                "ORDER BY reservation_count DESC " +
                "LIMIT 10";
        return makeJPAQueryFromNative(sql, null);
    }

    @Override
    public List<Restaurant> getTopTenByFavoriteOfUser(long userId) {
        String sql = "SELECT restaurant_id " +
                "FROM favorite " +
                "WHERE favorite.user_id = :userId " +
                " LIMIT 10";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return makeJPAQueryFromNative(sql, params);
    }

    @Override
    public List<Restaurant> getTopTenByReservationsOfUser(String username) {
        String sql = "SELECT restaurant.id " +
                "FROM restaurant JOIN reservation ON restaurant.id = reservation.restaurant_id " +
                "WHERE reservation.user_mail = :username " +
                "GROUP BY restaurant.id " +
                "ORDER BY COUNT(*) " +
                "LIMIT 10";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        return makeJPAQueryFromNative(sql, params);
    }

    @Override
    public List<Restaurant> getTopTenByZone(Zone key) {
        String sql = "SELECT id " +
                "FROM (SELECT r.id, (SELECT count(*) FROM favorite WHERE restaurant_id = r.id) " +
                "AS fav_count, (SELECT COALESCE(FLOOR(AVG(restaurant_review.rating)), 0) FROM restaurant_review WHERE restaurant_review.restaurant_id = r.id) AS rating " +
                "FROM restaurant r " +
                "WHERE zone_id = :zone) restaurant_favs " +
                "ORDER BY rating DESC, fav_count DESC " +
                "LIMIT 10";

        Map<String, Object> params = new HashMap<>();
        params.put("zone", key.getId());
        return makeJPAQueryFromNative(sql, params);
    }

    @Override
    public List<Restaurant> getTopTenByCategory(Category key) {
        String sql = "SELECT id " +
                "FROM (SELECT r.id, (SELECT count(*) FROM favorite WHERE restaurant_id = r.id) " +
                "AS fav_count, (SELECT COALESCE(FLOOR(AVG(restaurant_review.rating)), 0) FROM restaurant_review WHERE restaurant_review.restaurant_id = r.id) AS rating " +
                "FROM restaurant r " +
                "WHERE r.id IN (SELECT restaurant_id FROM restaurant_category WHERE category_id = :category)) restaurant_favs " +
                "ORDER BY rating DESC, fav_count DESC " +
                "LIMIT 10";


        Map<String, Object> params = new HashMap<>();
        params.put("category", key.getId());
        return makeJPAQueryFromNative(sql, params);
    }

    // String sql should always SELECT desired restaurants ids
    private List<Restaurant> makeJPAQueryFromNative(String sql, Map<String, Object> params) {
        Query query = em.createNativeQuery(sql);
        if (params != null) params.forEach(query::setParameter);
        List<Long> ids = new ArrayList<>();
        for (Object o : query.getResultList()) {
            ids.add(((Integer) o).longValue());
        }
        if (ids.isEmpty())
            return new ArrayList<>();

        TypedQuery<Restaurant> restaurants = em.createQuery("from Restaurant as r where r.id IN :ids", Restaurant.class);
        restaurants.setParameter("ids", ids);

        List<Restaurant> content = restaurants.getResultList();
        content.sort(RATING_FAVORITES_COMPARATOR);

        return content;
    }

    private static class ParametrizedQuery {
        public final String query;
        public final Map<String, Object> args;

        public ParametrizedQuery(String query, Map<String, Object> args) {
            this.query = query;
            this.args = args;
        }
    }
}
