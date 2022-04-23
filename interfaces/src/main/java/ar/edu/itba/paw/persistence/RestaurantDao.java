package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Category;
import ar.edu.itba.paw.model.OpeningHours;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Zone;

import java.util.List;
import java.util.Optional;

public interface RestaurantDao {

    Optional<Restaurant> getById(long id);

    Optional<Restaurant> getByUserId(long id);

    List<Restaurant> getAll(int page);

    List<Restaurant> filter(int page, String name, List<Category> categories, List<OpeningHours> openingHours, List<Zone> zones);

    Restaurant create(final long userID, final String name, final String address, final String mail, final String detail);
}
