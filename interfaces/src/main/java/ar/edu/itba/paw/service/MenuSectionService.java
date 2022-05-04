package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.MenuSection;

import java.util.List;

public interface MenuSectionService {

    List<MenuSection> getByRestaurantId(long restaurantId);

    MenuSection create(final long restaurantId, String name);

    boolean delete(long sectionId);

    boolean edit(long sectionId, String name, long restaurantId, long ordering);

}
