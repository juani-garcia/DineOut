package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.MenuSection;

import java.util.List;
import java.util.Optional;

public interface MenuSectionService {

    Optional<MenuSection> getById(final long sectionId);

    List<MenuSection> getByRestaurantId(long restaurantId);

    MenuSection create(String name);

    void delete(long sectionId);

    void update(long sectionId, String newName, List<Long> menuItemsOrder);

    void moveUp(final long sectionId);

    void moveDown(final long sectionId);

}
