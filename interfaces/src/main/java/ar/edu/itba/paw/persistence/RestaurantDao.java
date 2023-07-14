package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;

import java.util.List;
import java.util.Optional;

public interface RestaurantDao {

    Optional<Restaurant> getById(long id);

    Optional<Restaurant> getByMail(String mail);

    Optional<Restaurant> getByUserId(long id);

    PagedQuery<Restaurant> filter(int page, String name, Category category, Shift shift, Zone zone);

    Restaurant create(final User user, final String name, final Image image, final String address, final String mail, final String detail, final Zone zone, final Float lat, final Float lng);

    List<Restaurant> getTopTenByFavorite();

    List<Restaurant> getTopTenByReservations();

    List<Restaurant> getTopTenByFavoriteOfUser(long userId);

    List<Restaurant> getTopTenByReservationsOfUser(String username);

    List<Restaurant> getTopTenByZone(Zone key);

    List<Restaurant> getTopTenByCategory(Category key);
}
