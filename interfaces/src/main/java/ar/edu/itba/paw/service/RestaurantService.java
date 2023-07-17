package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    Optional<Restaurant> getById(long id);

    Optional<Restaurant> getByMail(String mail);

    PagedQuery<Restaurant> filter(int page, String name, Category category, Shift shift, Zone zone, Long favoriteOf);

    Restaurant create(final String name, final byte[] image, final String address, final String mail, final String detail, final Zone zone, final Float lat, final Float lng, final List<Long> categories, final List<Long> shifts);

    void updateCurrentRestaurant(final String name, final String address, final String mail, final String detail, final Zone zone, final Float lat, final Float lng, final List<Long> categories, final List<Long> shifts, byte[] imageBytes);

    Optional<Restaurant> getByUserID(long id);

    Restaurant getRecommendedRestaurant(boolean isDiner);

    Optional<Restaurant> getOfLoggedUser();

    void updateRestaurantImage(final long id, byte[] image);
}
