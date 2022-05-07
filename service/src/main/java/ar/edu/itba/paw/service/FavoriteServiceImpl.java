package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDao favoriteDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RestaurantService restaurantService;

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

    @Override
    public List<Restaurant> getRestaurantList(int page) {
        List<Restaurant> restaurantList = new ArrayList<>();
        for (Favorite favorite : getAllOfLoggedUser(page)) {
            restaurantList.add(restaurantService.getById(favorite.getRestaurantId()).orElseThrow(RestaurantNotFoundException::new));
        }
        return restaurantList;
    }

    @Override
    public List<Favorite> getAllOfLoggedUser(int page) {
        User user = securityService.getCurrentUser().orElseThrow(IllegalStateException::new);
        return favoriteDao.getAllByUserId(user.getId(), page);
    }

    @Override
    public long getFavoriteCount() {
        return favoriteDao.countByUserId(securityService.getCurrentUser().orElseThrow(IllegalStateException::new).getId());
    }
}
