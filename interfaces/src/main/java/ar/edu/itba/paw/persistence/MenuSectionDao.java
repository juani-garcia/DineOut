package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.MenuSection;

import java.util.List;

public interface MenuSectionDao {

    List<MenuSection> getByRestaurantId(long restaurantId);

    MenuSection create(String name, long restaurantId, long ordering);

    boolean delete(long sectionId);

    boolean edit(long sectionId, String name, long restaurantId, long ordering);

}
