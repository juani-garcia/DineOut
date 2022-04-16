package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.MenuSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MenuSectionJdbcDao implements MenuSectionDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<MenuSection> SECTION_ROW_MAPPER = (rs, rowNum) ->
            new MenuSection(rs.getLong("id"), rs.getString("name"), rs.getLong("restaurant_id"));

    @Autowired
    public MenuSectionJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("menu_section").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<MenuSection> getByRestaurantId(long restaurantId) {
        return jdbcTemplate.query("SELECT * FROM menu_section WHERE restaurant_id = ? ORDER BY ordering",
                new Object[]{restaurantId}, SECTION_ROW_MAPPER);
    }

    @Override
    public MenuSection create(String name, long restaurantId, long ordering) {
        final Map<String, Object> sectionData = new HashMap<>();
        sectionData.put("name", name);
        sectionData.put("restaurant_id", restaurantId);
        sectionData.put("ordering", ordering);
        final long sectionId = jdbcInsert.executeAndReturnKey(sectionData).longValue();

        return new MenuSection(sectionId, name, restaurantId);
    }

    @Override
    public boolean delete(long sectionId) {
        String query = "DELETE FROM menu_section WHERE id = ?";
        Object[] args = new Object[]{sectionId};
        return jdbcTemplate.update(query, args) == 1;
    }

    @Override
    public boolean edit(long sectionId, String name, long restaurantId, long ordering) {
        String query = "UPDATE menu_section SET name = ?, restaurant_id = ?, ordering = ? WHERE id = ?";
        Object[] args = new Object[]{name, restaurantId, ordering, sectionId};

        return jdbcTemplate.update(query, args) == 1;

    }


}
