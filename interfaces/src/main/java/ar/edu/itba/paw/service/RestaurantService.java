package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Filter;

public interface RestaurantService {

    Optional<Restaurant> getById(long id);

    Optional<Restaurant> getByMail(String mail);

    PagedQuery<Restaurant> filter(FilterParams params);

    Restaurant create(final String name, final byte[] image, final String address, final String mail, final String detail, final Zone zone, final Float lat, final Float lng, final List<Category> categories, final List<Shift> shifts);

    // TODO: change to updateRestaurant(long id, ...)
    void updateCurrentRestaurant(final String name, final String address, final String mail, final String detail, final Zone zone, final Float lat, final Float lng, final List<Category> categories, final List<Shift> shifts, final List<Long> menuSectionsOrder, byte[] imageBytes);

    Optional<Restaurant> getByUserID(long id);

    Optional<Restaurant> getOfLoggedUser();

    void updateRestaurantImage(final long id, byte[] image);
}
