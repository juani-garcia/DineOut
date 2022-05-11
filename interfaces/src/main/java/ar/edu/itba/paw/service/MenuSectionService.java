package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.MenuSection;

import java.util.List;
import java.util.Optional;

public interface MenuSectionService {

    Optional<MenuSection> getById(final long sectionId);

    List<MenuSection> getByRestaurantId(long restaurantId);

    MenuSection create(final long restaurantId, String name);

    boolean delete(long sectionId);

    boolean edit(long sectionId, String name, long restaurantId, long ordering);

    boolean updateName(long sectionId, String newName);

    boolean moveUp(final long sectionId);

    boolean moveDown(final long sectionId);

}
