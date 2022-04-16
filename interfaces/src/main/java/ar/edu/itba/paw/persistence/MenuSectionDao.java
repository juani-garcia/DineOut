package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.MenuSection;

import java.util.List;

public interface MenuSectionDao {

    List<MenuSection> getByRestaurantId(long restaurantId);

}
