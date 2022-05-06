package ar.edu.itba.paw.persistence;

public interface FavoriteDao {

    boolean delete(long restaurantId, long userId);

    boolean create(long restaurantId, long userId);

    Long getFavCount(long restaurantId);

    boolean isFavorite(long restaurantId, long userId);
}
