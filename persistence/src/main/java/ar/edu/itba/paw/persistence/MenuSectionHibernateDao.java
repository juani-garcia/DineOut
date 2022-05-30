package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.MenuSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MenuSectionHibernateDao implements MenuSectionDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<MenuSection> getById(final long menuSectionId) {
        return Optional.ofNullable(em.find(MenuSection.class, menuSectionId));
    }

    @Override
    public void delete(final long menuSectionId) {
        em.remove(em.find(MenuSection.class, menuSectionId));
    }

}
