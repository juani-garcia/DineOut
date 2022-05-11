package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.MenuSection;

import java.util.List;
import java.util.Optional;

public interface MenuSectionDao {

    List<MenuSection> getByRestaurantId(long restaurantId);

    MenuSection create(final long restaurantId, final String name);

    Optional<MenuSection> getById(final long sectionId);

    boolean delete(long sectionId);

    boolean edit(long sectionId, String name, long restaurantId, long ordering);

}
