package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.FavoriteDao;
import ar.edu.itba.paw.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl implements FavoriteService{

    @Autowired
    private FavoriteDao favoriteDao;

    @Autowired
    private SecurityService securityService;

    @Override
    public boolean delete(long restaurantId, long userId) {
        return favoriteDao.delete(restaurantId, userId);
    }

    @Override
    public boolean create(long restaurantId, long userId) {
        return favoriteDao.create(restaurantId, userId);
    }

    @Override
    public boolean isFavoriteOfLoggedUser(long resId) {
        User user = securityService.getCurrentUser().orElse(null);
        if (user == null) return false;
        return favoriteDao.isFavorite(resId, user.getId());
    }

    @Override
    public Long getFavCount(long restaurantId) {
        return favoriteDao.getFavCount(restaurantId);
    }
}
