package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistence.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<Restaurant> filter(int page, String name, long categoryId, long shiftId, long zoneId) {
        Category category = Category.getByOrdinal(categoryId);
        Zone zone = Zone.getByOrdinal(zoneId);
        Shift shift = Shift.getById(shiftId);

        System.out.println("**************************************");
        System.out.println("Category = " + category + " " + categoryId);
        System.out.println("Zone = " + zone + " " + zoneId);
        System.out.println("Shift = " + shift + " " + shiftId);
        System.out.println("**************************************");

        if(name == null && category == null && zone == null && shift == null) {
            return getAll(page);
        }

        return restaurantDao.filter(page, name, category, shift, zone);
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
