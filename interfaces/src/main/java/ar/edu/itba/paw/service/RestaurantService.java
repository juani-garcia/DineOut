package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    Optional<Restaurant> getById(long id);

    List<Restaurant> getAll(int page);

    Restaurant create(final long userID, final String name, final String address, final String mail, final String detail);

    Optional<Restaurant> getByUserID(long id);
}
