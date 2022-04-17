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
            new Category(rs.getString("name"), rs.getLong("id"));

    @Autowired
    public CategoryJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<Category> getAll() {
        return jdbcTemplate.query("SELECT * FROM category", CATEGORY_ROW_MAPPER);
    }

    @Override
    public Optional<Category> getById(long id) {
        List<Category> result = jdbcTemplate.query("SELECT * FROM category WHERE id = ?", new Object[]{id}, CATEGORY_ROW_MAPPER);
        return result.stream().findFirst();
    }

    @Override
    public List<Category> getByRestaurantId(long restaurantId) {
        String query = "SELECT c.id, c.name FROM restaurant_category rc JOIN category c ON rc.category_id = c.id WHERE rc.restaurant_id = ?";
        return jdbcTemplate.query(query, new Object[]{restaurantId}, CATEGORY_ROW_MAPPER);
    }

    @Override
    public boolean delete(long restaurantId, long categoryId) {
        String sql = "DELETE FROM restaurant_category WHERE restaurant_id = ? AND category_id = ?";
        Object[] args = new Object[]{restaurantId, categoryId};
        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public boolean add(long restaurantId, long categoryId) {
        String sql = "INSERT INTO restaurant_category VALUES (?, ?) ON CONFLICT DO NOTHING";
        Object[] args = new Object[]{restaurantId, categoryId};
        return jdbcTemplate.update(sql, args) == 1;
    }
}
