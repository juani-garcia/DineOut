package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemService {

    Optional<MenuItem> getById(final long itemId);

    List<MenuItem> getBySectionId(long sectionId);

    MenuItem create(String name, String detail, double price, long sectionId, byte[] imageBytes);

    void delete(long itemId);

    void edit(long menuItemId, String name, String detail, double price, long menuSectionId, byte[] imageBytes);

    void moveUp(final long itemId);

    void moveDown(final long itemId);
}
