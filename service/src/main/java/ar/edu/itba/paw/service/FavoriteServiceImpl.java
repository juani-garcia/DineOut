package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Favorite;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.InvalidPageException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public boolean delete(long restaurantId, long userId) {
        return favoriteDao.delete(restaurantId, userId);
    }

    @Transactional
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
        if (page <= 0) throw new InvalidPageException();
        List<Restaurant> restaurantList = new ArrayList<>();
        for (Favorite favorite : getAllOfLoggedUser(page)) {
            restaurantList.add(restaurantService.getById(favorite.getRestaurantId()).orElseThrow(NotFoundException::new));
        }
        return restaurantList;
    }

    @Override
    public List<Favorite> getAllOfLoggedUser(int page) {
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        return favoriteDao.getAllByUserId(user.getId(), page);
    }

    @Override
    public long getFavoriteCount() {
        return favoriteDao.countByUserId(securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new).getId());
    }

    @Override
    public long getFavoritePageCount() {
        return favoriteDao.countPagesByUserId(securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new).getId());
    }

    @Transactional
    @Override
    public void set(long resId, long id, boolean set) {
        if (set) {
            create(resId, securityService.getCurrentUser().orElseThrow(IllegalArgumentException::new).getId());
        } else {
            delete(resId, securityService.getCurrentUser().orElseThrow(IllegalArgumentException::new).getId());
        }
    }
}
