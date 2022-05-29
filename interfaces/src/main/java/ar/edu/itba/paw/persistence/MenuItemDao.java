package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.MenuItem;

import java.util.Optional;

public interface MenuItemDao {

    Optional<MenuItem> getById(final long itemId);

    void delete(long itemId);

}
