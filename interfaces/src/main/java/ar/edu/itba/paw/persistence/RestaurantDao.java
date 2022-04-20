package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantDao {

    Optional<Restaurant> getById(long id);

    List<Restaurant> getAll(int page);

    Restaurant create(final String name, final String address, final String mail, final String detail);
}
