package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MenuItemJdbcDao implements MenuItemDao {

    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<MenuItem> ITEM_ROW_MAPPER = (rs, rowNum) ->
            new MenuItem(rs.getString("name"), rs.getString("detail"),
                    rs.getDouble("price"), rs.getLong("section_id"));

    @Autowired
    public MenuItemJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<MenuItem> getBySectionId(long sectionId) {
        return jdbcTemplate.query("SELECT * FROM menu_item WHERE section_id = ? ORDER BY ordering",
                new Object[]{sectionId}, ITEM_ROW_MAPPER);
    }
}
