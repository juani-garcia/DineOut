package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.MenuSection;

import java.util.List;

public interface MenuSectionService {

    List<MenuSection> getByRestaurantId(long restaurantId);

}
