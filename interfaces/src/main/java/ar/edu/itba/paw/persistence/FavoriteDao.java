package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Favorite;

import java.util.List;

public interface FavoriteDao {

    boolean delete(long restaurantId, long userId);

    boolean create(long restaurantId, long userId);

    Long getFavCount(long restaurantId);

    boolean isFavorite(long restaurantId, long userId);

    List<Favorite> getAllByUserId(long id, int page);

    long countByUserId(long id);

    long countPagesByUserId(long id);
}
