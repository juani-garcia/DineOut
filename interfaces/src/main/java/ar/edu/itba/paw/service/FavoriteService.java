package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Favorite;
import ar.edu.itba.paw.persistence.Reservation;
import ar.edu.itba.paw.persistence.Restaurant;

import java.util.List;

public interface FavoriteService {

    boolean delete(long restaurantId, long userId);

    boolean create(long restaurantId, long userId);

    boolean isFavoriteOfLoggedUser(long resId);

    Long getFavCount(long restaurantId);

    List<Restaurant> getRestaurantList(int page);

    List<Favorite> getAllOfLoggedUser(int page);

    long getFavoriteCount();
}
