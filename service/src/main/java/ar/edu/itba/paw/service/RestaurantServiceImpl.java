package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistence.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Override
    public Optional<Restaurant> getById(long id) {
        return restaurantDao.getById(id);
    }

    @Override
    public List<Restaurant> getAll(int page) {
        return restaurantDao.getAll(page);
    }

    @Override
    public List<Restaurant> filter(int page, String name, List<Category> categories, List<Shift> openingHours, List<Zone> zones) {
        if(name == null && categories == null && openingHours == null && zones == null) {
            return getAll(page);
        }

        return restaurantDao.filter(page, name, categories, openingHours, zones);
    }

    @Override
    public Restaurant create(long userID, String name, String address, String mail, String detail) {
        // TODO : validate data
        return restaurantDao.create(userID, name, address, mail, detail);
    }

    @Override
    public Optional<Restaurant> getByUserID(long id) {
        return restaurantDao.getByUserId(id);
    }
}
