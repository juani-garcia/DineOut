package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryJdbcDao implements CategoryDao {

    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, rowNum) ->
            Category.getById(rs.getLong("id"));

    @Autowired
    public CategoryJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<Category> getByRestaurantId(long restaurantId) {
        String query = "SELECT category_id id FROM restaurant_category WHERE restaurant_id = ?";
        return jdbcTemplate.query(query, new Object[]{restaurantId}, CATEGORY_ROW_MAPPER);
    }

    @Override
    public boolean delete(long restaurantId, Category category) {
        String sql = "DELETE FROM restaurant_category WHERE restaurant_id = ? AND category_id = ?";
        Object[] args = new Object[]{restaurantId, category.getId()};
        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public boolean add(long restaurantId, Category category) {
        String sql = "INSERT INTO restaurant_category VALUES (?, ?) ON CONFLICT DO NOTHING";
        Object[] args = new Object[]{restaurantId, category.getId()};
        return jdbcTemplate.update(sql, args) == 1;
    }
}
