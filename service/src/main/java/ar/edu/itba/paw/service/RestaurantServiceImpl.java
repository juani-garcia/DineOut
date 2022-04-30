package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistence.Restaurant;
import ar.edu.itba.paw.persistence.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private CategoryService categoryService;

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
    public List<Restaurant> filter(int page, String name, String categoryName, String shiftName, String zoneName) {
        Category category = Category.getByName(categoryName);
        Zone zone = Zone.getByName(zoneName);
        Shift shift = Shift.getByName(shiftName);

        return restaurantDao.filter(page, name, category, shift, zone);
    }

    @Override
    public Restaurant create(long userID, String name, String address, String mail, String detail, Zone zone, final List<Long> categories) {
        // TODO : validate data
        Restaurant restaurant = restaurantDao.create(userID, name, address, mail, detail, zone);
        for (Long categoryId : categories) {
            Category category = Category.getByOrdinal(categoryId);
            categoryService.add(restaurant.getId(), category);
        }
        return restaurant;
    }

    @Override
    public Optional<Restaurant> getByUserID(long id) {
        return restaurantDao.getByUserId(id);
    }
}
