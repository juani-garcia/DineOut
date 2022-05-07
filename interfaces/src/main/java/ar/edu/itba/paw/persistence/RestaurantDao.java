package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Category;
import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.model.Zone;

import java.util.List;
import java.util.Optional;

public interface RestaurantDao {

    Optional<Restaurant> getById(long id);

    Optional<Restaurant> getByMail(String mail);

    Optional<Restaurant> getByUserId(long id);

    List<Restaurant> getAll(int page);

    List<Restaurant> filter(int page, String name, Category category, Shift shift, Zone zone);

    Restaurant create(final long userID, final String name, final String address, final String mail, final String detail, final Zone zone);

    Long getCount();

    Long getFilteredCount(String name, Category category, Shift shift, Zone zone);

    List<Restaurant> getTopTenByFavorite();

    List<Restaurant> getTopTenByReservations();

    List<Restaurant> getTopTenByFavoriteOfUser(long userId);

    List<Restaurant> getTopTenByReservationsOfUser(long userId);
}
