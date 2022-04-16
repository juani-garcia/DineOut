package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.MenuSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MenuSectionJdbcDao implements MenuSectionDao {

    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<MenuSection> SECTION_ROW_MAPPER = (rs, rowNum) ->
            new MenuSection(rs.getLong("id"), rs.getString("name"), rs.getLong("restaurant_id"));

    @Autowired
    public MenuSectionJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<MenuSection> getByRestaurantId(long restaurantId) {
        return jdbcTemplate.query("SELECT * FROM menu_section WHERE restaurant_id = ? ORDER BY ordering",
                new Object[]{restaurantId}, SECTION_ROW_MAPPER);
    }
}
