package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Category;
import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Zone;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    Optional<Restaurant> getById(long id);

    List<Restaurant> getAll(int page);

    List<Restaurant> filter(int page, String name, String categoryName, String shiftName, String zoneName);

    Restaurant create(final long userID, final String name, final String address, final String mail, final String detail, final Zone zone, final List<Long> categories);

    Optional<Restaurant> getByUserID(long id);
}
