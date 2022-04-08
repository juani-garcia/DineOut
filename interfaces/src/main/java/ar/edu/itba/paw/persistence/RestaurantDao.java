package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Restaurant;

import java.util.Optional;

public interface RestaurantDao {

    Optional<Restaurant> getRestaurantById(long id);

}
