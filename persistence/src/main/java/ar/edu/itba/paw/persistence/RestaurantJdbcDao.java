package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Category;
import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.model.Restaurant;
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
    private static final int PAGE_SIZE = 10;
    private final SimpleJdbcInsert jdbcInsert;
    /* private X default=package-private for testing */
    static final RowMapper<Restaurant> ROW_MAPPER = (rs, rowNum) ->
            new Restaurant(rs.getLong("id"), rs.getLong("user_id"), rs.getString("name"), rs.getString("address"),
            rs.getString("mail"), rs.getString("detail"), Zone.getByOrdinal(rs.getLong("zone_id")));

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
    public Optional<Restaurant> getByUserId(long id) {
        List<Restaurant> query = jdbcTemplate.query("SELECT * FROM restaurant WHERE user_id = ?", new Object[]{id}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public List<Restaurant> getAll(int page) {
        return jdbcTemplate.query("SELECT * FROM restaurant ORDER BY name LIMIT ? OFFSET ?", new Object[] {PAGE_SIZE, (page - 1) * PAGE_SIZE},  ROW_MAPPER);
    }

    @Override
    public List<Restaurant> filter(int page, String name, Category category, Shift shift, Zone zone) {
        List<Object> args = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT id, name, address, mail, detail, zone_id, user_id\n");

        sql.append("FROM restaurant\n");
        sql.append("WHERE true\n");

        if(name != null) {
            sql.append("AND LOWER(name) like ?\n");
            args.add('%' + name.toLowerCase() + '%');
        }

        // TODO:
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

        sql.append("LIMIT ? OFFSET ?");
        args.add(PAGE_SIZE);
        args.add((page - 1) * PAGE_SIZE);

        System.out.println("---------------------------------------");
        System.out.println(sql);
        System.out.println(args);
        System.out.println("---------------------------------------");

        return jdbcTemplate.query(sql.toString(), args.toArray(), ROW_MAPPER);
    }

    @Override
    public Restaurant create(final long userID, final String name, final String address, final String mail, final String detail) {
        final Map<String, Object> restaurantData = new HashMap<>();
        restaurantData.put("user_id", userID);
        restaurantData.put("name", name);
        restaurantData.put("address", address);
        restaurantData.put("mail", mail);
        restaurantData.put("detail", detail);

        final long restaurantid = jdbcInsert.executeAndReturnKey(restaurantData).longValue();
        return new Restaurant(restaurantid, userID, name, address, mail, detail, null);
    }
}
