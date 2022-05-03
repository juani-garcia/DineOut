package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.DuplicatedMailException;
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
    private ShiftService shiftService;

    @Autowired
    private RestaurantDao restaurantDao;

    @Override
    public Optional<Restaurant> getById(long id) {
        return restaurantDao.getById(id);
    }

    @Override
    public Optional<Restaurant> getByMail(String mail) {
        return restaurantDao.getByMail(mail);
    }

    @Override
    public List<Restaurant> getAll(int page) {
        return restaurantDao.getAll(page);
    }

    @Override
    public List<Restaurant> filter(int page, String name, int categoryId, int shiftId, int zoneId) {
        Category category = Category.getById(categoryId);
        Zone zone = Zone.getById(zoneId);
        Shift shift = Shift.getById(shiftId);

        return restaurantDao.filter(page, name, category, shift, zone);
    }

    @Override
    public Restaurant create(long userID, String name, String address, String mail, String detail, Zone zone, final List<Long> categories, final List<Long> shifts) {
        if(getByMail(mail).isPresent()) {
            DuplicatedMailException ex = new DuplicatedMailException();
            ex.setAddress(address);
            ex.setCategories(categories);
            ex.setName(name);
            ex.setMail(mail);
            ex.setDetail(detail);
            ex.setZone(zone);
            throw ex;
        }

        Restaurant restaurant = restaurantDao.create(userID, name, address, mail, detail, zone);
        for (Long categoryId : categories) {
            Category category = Category.getById(categoryId);
            categoryService.add(restaurant.getId(), category);
        }
        for (Long shiftId : shifts) {
            Shift shift = Shift.getById(shiftId);
            shiftService.add(restaurant.getId(), shift);
        }
        return restaurant;
    }

    @Override
    public Optional<Restaurant> getByUserID(long id) {
        return restaurantDao.getByUserId(id);
    }
}
