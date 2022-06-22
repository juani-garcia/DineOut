package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;

public interface FavoriteDao {

    Favorite create(User user, Restaurant restaurant);

    void delete(User user, Restaurant restaurant);

    boolean isFavorite(long restaurantId, long userId);

    PagedQuery<Favorite> getAllByUserId(long userId, int page);

}
