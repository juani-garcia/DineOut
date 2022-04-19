package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.MenuItem;
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
public class MenuItemJdbcDao implements MenuItemDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<MenuItem> ITEM_ROW_MAPPER = (rs, rowNum) ->
            new MenuItem(rs.getLong("id"), rs.getString("name"),
                    rs.getString("detail"), rs.getDouble("price"),
                    rs.getLong("section_id"));

    @Autowired
    public MenuItemJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("menu_item").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<MenuItem> getBySectionId(long sectionId) {
        return jdbcTemplate.query("SELECT * FROM menu_item WHERE section_id = ? ORDER BY ordering",
                new Object[]{sectionId}, ITEM_ROW_MAPPER);
    }

    @Override
    public MenuItem create(String name, String detail, double price, long sectionId, long ordering) {
        Map<String, Object> itemData = new HashMap<>();
        itemData.put("name", name);
        itemData.put("detail", detail);
        itemData.put("price", price);
        itemData.put("section_id", sectionId);
        itemData.put("ordering", ordering);
        long itemId = jdbcInsert.executeAndReturnKey(itemData).longValue();

        return new MenuItem(itemId, name, detail, price, sectionId);
    }

    @Override
    public boolean delete(long itemId) {
        String query = "DELETE FROM menu_item WHERE id = ?";
        Object[] args = new Object[]{itemId};
        return jdbcTemplate.update(query, args) == 1;
    }

    @Override
    public boolean edit(long itemId, String name, String detail, double price, long sectionId, long ordering) {
        String query = "UPDATE menu_item SET name = ?, detail = ?, price = ?, section_id = ?, ordering = ? WHERE id = ?";
        Object[] args = new Object[]{name, detail, price, sectionId, ordering, itemId};

        return jdbcTemplate.update(query, args) == 1;
    }

}