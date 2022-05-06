package ar.edu.itba.paw.service;

public interface FavoriteService {

    boolean delete(long restaurantId, long userId);

    boolean create(long restaurantId, long userId);

    boolean isFavoriteOfLoggedUser(long resId);

    Long getFavCount(long restaurantId);

}
