package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RestaurantJdbcDao implements RestaurantDao {

    private final JdbcTemplate jdbcTemplate;
    private static final int PAGE_SIZE = 10;
    private final SimpleJdbcInsert jdbcInsert;
    /* private X default=package-private for testing */
    static final RowMapper<Restaurant> ROW_MAPPER = (rs, rowNum) ->
            new Restaurant(rs.getLong("id"), rs.getLong("user_id"), rs.getString("name"), rs.getString("address"),
            rs.getString("mail"), rs.getString("detail"));

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
    public Restaurant create(final long userID, final String name, final String address, final String mail, final String detail) {
        final Map<String, Object> restaurantData = new HashMap<>();
        restaurantData.put("user_id", userID);
        restaurantData.put("name", name);
        restaurantData.put("address", address);
        restaurantData.put("mail", mail);
        restaurantData.put("detail", detail);

        final long restaurantId = jdbcInsert.executeAndReturnKey(restaurantData).longValue();
        return new Restaurant(restaurantId, userID, name, address, mail, detail);
    }
}
