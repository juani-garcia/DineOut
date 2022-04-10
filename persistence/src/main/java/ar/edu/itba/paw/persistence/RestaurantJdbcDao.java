package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class RestaurantJdbcDao implements RestaurantDao {

    private final JdbcTemplate jdbcTemplate;
    private static final int PAGE_SIZE = 10;
    private static final RowMapper<Restaurant> ROW_MAPPER = (rs, rowNum) -> new Restaurant(rs.getLong("id"), rs.getString("name"), rs.getString("address"), rs.getString("detail"), null);

    @Autowired
    public RestaurantJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Optional<Restaurant> getRestaurantById(long id) {
        List<Restaurant> query = jdbcTemplate.query("SELECT * FROM restaurant WHERE id = ?", new Object[]{id}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public List<Restaurant> getAll(int page) {
        return jdbcTemplate.query("SELECT * FROM restaurant ORDER BY name LIMIT ? OFFSET ?", new Object[] {PAGE_SIZE, (page - 1) * PAGE_SIZE},  ROW_MAPPER);
    }
}
