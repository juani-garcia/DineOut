package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Image;
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
public class ImageJdbcDao implements ImageDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<Image> IMAGE_ROW_MAPPER = (rs, rowNum) ->
            new Image(rs.getLong("id"), rs.getBytes("source"));

    @Autowired
    public ImageJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("image").usingGeneratedKeyColumns("id");
    }

    @Override
    public Image create(final byte[] source) {
        Map<String, Object> imageData = new HashMap<>();
        imageData.put("source", source);
        long imageId = jdbcInsert.executeAndReturnKey(imageData).longValue();

        return new Image(imageId, source);
    }

    @Override
    public Optional<Image> getById(final long id) {
        List<Image> query = jdbcTemplate.query("SELECT * FROM image WHERE id = ?", new Object[] {id}, IMAGE_ROW_MAPPER);
        return query.stream().findFirst();
    }


    @Override
    public boolean edit(final long id, final byte[] source) {
        return jdbcTemplate.update("UPDATE image SET source = ? WHERE id = ?", new Object[]{source, id}) > 0;
    }

    @Override
    public boolean delete(final long id) {
        return jdbcTemplate.update("DELETE FROM image WHERE id = ?", new Object[]{id}) > 0;
    }

}
