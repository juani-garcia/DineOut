package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Category;
import ar.edu.itba.paw.model.OpeningHours;
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
    public List<Restaurant> filter(int page, String name, List<Category> categories, List<OpeningHours> openingHours, List<Zone> zones) {
        List<Object> args = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT DISTINCT r.id AS id, r.name AS name, r.address AS address,");
        sql.append("r.mail AS mail, r.detail AS detail, r.zone_id AS zone_id, r.user_id AS user_id\n");

        sql.append("FROM (restaurant r LEFT OUTER JOIN restaurant_category rc ON r.id = rc.restaurant_id)\n");
        sql.append("LEFT OUTER JOIN restaurant_opening_hours roh ON r.id = roh.restaurant_id\n");
        sql.append("WHERE true\n");

        if(name != null) {
            sql.append("AND LOWER(r.name) like ?\n");
            args.add('%' + name.toLowerCase() + '%');
        }

        // TODO:
        if(categories != null) {
            sql.append("AND (");
            for(Category category : categories) {
                sql.append(" rc.category_id = ? OR");
                args.add(category.ordinal());
            }
            sql.delete(sql.length() - 2, sql.length());
            sql.append(")\n");
        }

        if(openingHours != null) {
            sql.append("AND (");
            for(OpeningHours hours : openingHours) {
                sql.append(" roh.opening_hours_id = ? OR");
                args.add(hours.ordinal());
            }
            sql.delete(sql.length() - 2, sql.length());
            sql.append(")\n");
        }

        if(zones != null) {
            sql.append("AND (");
            for(Zone zone : zones) {
                sql.append(" r.zone_id = ? OR");
                args.add(zone.ordinal());
            }
            sql.delete(sql.length() - 2, sql.length());
            sql.append(")\n");
        }

        sql.append("LIMIT ? OFFSET ?");
        args.add(PAGE_SIZE);
        args.add((page - 1) * PAGE_SIZE);

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
