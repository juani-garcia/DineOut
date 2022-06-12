package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Favorite;
import ar.edu.itba.paw.model.PagedQuery;

public interface FavoriteService {

    boolean isFavoriteOfLoggedUser(long resId);

    PagedQuery<Favorite> getAllOfLoggedUser(int page);

    void set(long resId, long id, boolean set);
}
