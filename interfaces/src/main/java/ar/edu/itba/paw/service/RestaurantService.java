package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Restaurant;
import ar.edu.itba.paw.model.Zone;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    Optional<Restaurant> getById(long id);

    Optional<Restaurant> getByMail(String mail);

    List<Restaurant> getAll(int page);

    List<Restaurant> filter(int page, String name, int category, int shift, int zone);

    Restaurant create(final long userID, final String name, final String address, final String mail, final String detail, final Zone zone, final List<Long> categories);

    Optional<Restaurant> getByUserID(long id);
}
