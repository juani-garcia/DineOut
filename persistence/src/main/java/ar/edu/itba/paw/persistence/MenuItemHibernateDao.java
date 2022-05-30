package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.MenuItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class MenuItemHibernateDao implements MenuItemDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<MenuItem> getById(final long menuItemId) {
        return Optional.ofNullable(em.find(MenuItem.class, menuItemId));
    }

    @Override
    public void delete(final long menuItemId) {
        em.remove(em.find(MenuItem.class, menuItemId));
    }

}
