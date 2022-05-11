package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemService {

    Optional<MenuItem> getById(final long itemId);

    List<MenuItem> getBySectionId(long sectionId);

    MenuItem create(String name, String detail, double price, long sectionId, byte[] imageBytes);

    boolean delete(long itemId);

    boolean edit(long itemId, String name, String detail, double price, long sectionId, byte[] imageBytes);

    boolean moveUp(final long itemId);

    boolean moveDown(final long itemId);
}
