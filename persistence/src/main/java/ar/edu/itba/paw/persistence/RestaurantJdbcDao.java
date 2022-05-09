package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Category;
import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class RestaurantJdbcDao implements RestaurantDao {

    private final JdbcTemplate jdbcTemplate;
    private static final int PAGE_SIZE = 3;
    private final SimpleJdbcInsert jdbcInsert;
    /* private X default=package-private for testing */
    static final RowMapper<Restaurant> ROW_MAPPER = (rs, rowNum) ->
            new Restaurant(rs.getLong("id"), rs.getLong("user_id"), rs.getString("name"), rs.getString("address"),
            rs.getString("mail"), rs.getString("detail"), Zone.getById(rs.getLong("zone_id")));

    @Autowired
    public RestaurantJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("restaurant").usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Restaurant> getById(long id) {
        List<Restaurant> query = jdbcTemplate.query("SELECT * FROM restaurant WHERE id = ?", new Object[]{id}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public Optional<Restaurant> getByMail(String mail) {
        List<Restaurant> query = jdbcTemplate.query("SELECT * FROM restaurant WHERE mail = ?", new Object[]{mail}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public Optional<Restaurant> getByUserId(long id) {
        List<Restaurant> query = jdbcTemplate.query("SELECT * FROM restaurant WHERE user_id = ?", new Object[]{id}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public List<Restaurant> getAll(int page) {
        return jdbcTemplate.query("SELECT * FROM restaurant ORDER BY name LIMIT ? OFFSET ?", new Object[] {PAGE_SIZE, (page - 1) * PAGE_SIZE},  ROW_MAPPER);
    }

    private static class Pair<A,B> {
        public final A left;
        public final B right;

        public Pair(A left, B right) {
            this.left = left;
            this.right = right;
        }
    };

    private Pair<StringBuilder, List<Object>> filterBuilder(String name, Category category, Shift shift, Zone zone, StringBuilder sql, List<Object> args) {
        sql.append("FROM restaurant\n");
        sql.append("WHERE true\n");

        if(name != null) {
            sql.append("AND LOWER(name) like ?\n");
            args.add('%' + name.toLowerCase() + '%');
        }

        if(category != null) {
            sql.append("AND id in (SELECT restaurant_id FROM restaurant_category WHERE category_id = ?)\n");
            args.add(category.getId());
        }

        if(shift != null) {
            sql.append("AND id in (SELECT restaurant_id FROM restaurant_opening_hours WHERE opening_hours_id = ?)\n");
            args.add(shift.getId());
        }

        if(zone != null) {
            sql.append("AND zone_id = ?\n");
            args.add(zone.getId());
        }
        return new Pair<>(sql, args);
    }

    @Override
    public List<Restaurant> filter(int page, String name, Category category, Shift shift, Zone zone) {
        StringBuilder sql = new StringBuilder("SELECT id, name, address, mail, detail, zone_id, user_id\n");
        List<Object> args = new ArrayList<>();

        Pair<StringBuilder, List<Object>> filterPair = this.filterBuilder(name, category, shift, zone, sql, args);

        sql = filterPair.left;
        args = filterPair.right;
        sql.append("LIMIT ? OFFSET ?");
        args.add(PAGE_SIZE);
        args.add((page - 1) * PAGE_SIZE);

        return jdbcTemplate.query(sql.toString(), args.toArray(), ROW_MAPPER);
    }

    @Override
    public Restaurant create(final long userID, final String name, final String address, final String mail, final String detail, final Zone zone) {
        final Map<String, Object> restaurantData = new HashMap<>();
        restaurantData.put("user_id", userID);
        restaurantData.put("name", name);
        restaurantData.put("address", address);
        restaurantData.put("mail", mail);
        restaurantData.put("detail", detail);
        restaurantData.put("zone_id", zone != null ? zone.getId() : null);

        final long restaurantId = jdbcInsert.executeAndReturnKey(restaurantData).longValue();
        return new Restaurant(restaurantId, userID, name, address, mail, detail, zone);
    }

    @Override
    public boolean update(final long restaurantId, final String name, final String address, final String mail, final String detail, final Zone zone) {
        String query = "UPDATE restaurant SET name = ?, address = ?, mail = ?, detail = ?, zone_id = ? WHERE id = ?";
        Object[] args = new Object[]{name, address, mail, detail, zone != null ? zone.getId() : null, restaurantId};

        return jdbcTemplate.update(query, args) == 1;
    }

    @Override
    public Long getCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM restaurant",  Long.class);
    }

    @Override
    public Long getFilteredCount(String name, Category category, Shift shift, Zone zone) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*)\n");
        List<Object> args = new ArrayList<>();

        Pair<StringBuilder, List<Object>> filterPair = this.filterBuilder(name, category, shift, zone, sql, args);

        sql = filterPair.left;
        args = filterPair.right;
        return jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Long.class);
    }

    @Override
    public List<Restaurant> getTopTenByFavorite() {
        return jdbcTemplate.query("SELECT * FROM restaurant NATURAL JOIN (" +
                "    SELECT restaurant_id AS id, COUNT(user_id) AS likes" +
                "    FROM favorite" +
                "    GROUP BY restaurant_id" +
                ") AS restaurant_with_likes" +
                " ORDER BY likes DESC" +
                " LIMIT 10", ROW_MAPPER);
    }

    @Override
    public List<Restaurant> getTopTenByFavoriteOfUser(long userId) {
        return jdbcTemplate.query("SELECT * FROM restaurant NATURAL JOIN (" +
                "    SELECT restaurant_id AS id" +
                "    FROM favorite" +
                "    WHERE user_id = ?" +
                "    GROUP BY restaurant_id" +
                ") AS fav_restaurants" +
                " LIMIT 10", new Object[]{userId}, ROW_MAPPER);
    }

    @Override
    public List<Restaurant> getTopTenByReservations() {
        return jdbcTemplate.query("SELECT * FROM restaurant NATURAL JOIN (" +
                "    SELECT restaurant_id AS id, COUNT(user_mail) AS reservation_count" +
                "    FROM reservation" +
                "    GROUP BY restaurant_id" +
                ") AS restaurant_with_reservation_count" +
                " ORDER BY reservation_count DESC" +
                " LIMIT 10", ROW_MAPPER);
    }

    @Override
    public List<Restaurant> getTopTenByReservationsOfUser(long userId) {
        return jdbcTemplate.query("SELECT * FROM restaurant NATURAL JOIN (" +
                "    SELECT restaurant_id AS id, COUNT(user_mail) AS reservation_count" +
                "    FROM reservation" +
                "    WHERE user_mail = ?" +
                "    GROUP BY restaurant_id" +
                ") AS restaurant_with_reservation_count" +
                " ORDER BY reservation_count DESC" +
                " LIMIT 10", new Object[]{userId}, ROW_MAPPER);
    }

    @Override
    public long getFilteredPagesCount(String name, Category category, Shift shift, Zone zone) {
        return Double.valueOf(Math.ceil(getFilteredCount(name, category, shift, zone).doubleValue() / PAGE_SIZE)).longValue();
    }

}
