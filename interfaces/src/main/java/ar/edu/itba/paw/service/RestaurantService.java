package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    Optional<Restaurant> getById(long id);

    List<Restaurant> getAll(int page);

}
