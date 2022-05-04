package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.FavoriteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl implements FavoriteService{

    @Autowired
    private FavoriteDao favoriteDao;

    @Override
    public boolean delete(long restaurantId, long userId) {
        return favoriteDao.delete(restaurantId, userId);
    }

    @Override
    public boolean create(long restaurantId, long userId) {
        return favoriteDao.create(restaurantId, userId);
    }

    @Override
    public Long getFavCount(long restaurantId) {
        return favoriteDao.getFavCount(restaurantId);
    }
}
