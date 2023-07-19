package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Favorite;
import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.InvalidPageException;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.persistence.FavoriteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDao favoriteDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public boolean isFavoriteOfUser(long userId, long resId) {
        return favoriteDao.isFavorite(resId, userId);
    }

    @Override
    public boolean isFavoriteOfLoggedUser(long resId) {
        User user = securityService.getCurrentUser().orElse(null);
        if (user == null) return false;
        return favoriteDao.isFavorite(resId, user.getId());
    }

    @Override
    public PagedQuery<Favorite> getAllOfLoggedUser(int page) {
        if (page <= 0) throw new InvalidPageException();
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        return favoriteDao.getAllByUserId(user.getId(), page);
    }

    @Transactional
    @Override
    public void set(final long resId, final long id, final boolean set) {
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        Restaurant restaurant = restaurantService.getById(resId).orElseThrow(IllegalArgumentException::new);
        if (set == isFavoriteOfUser(id, resId))
            return;
        if (set) {
            favoriteDao.create(user, restaurant);
        } else {
            favoriteDao.delete(user, restaurant);
        }
    }
}
