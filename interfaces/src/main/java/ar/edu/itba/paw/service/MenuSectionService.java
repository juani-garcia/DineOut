package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.MenuSection;

import java.util.List;

public interface MenuSectionService {

    List<MenuSection> getByRestaurantId(long restaurantId);

    MenuSection create(String name, long restaurantId, long ordering);

    boolean delete(long sectionId);

    boolean edit(long sectionId, String name, long restaurantId, long ordering);

}
