package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private FavoriteService favoriteService;

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

    @Override
    public Long getCount() {
        return restaurantDao.getCount();
    }

    @Override
    public Long getFilteredCount(String name, int categoryId, int shiftId, int zoneId) {
        Category category = Category.getById(categoryId);
        Zone zone = Zone.getById(zoneId);
        Shift shift = Shift.getById(shiftId);

        return restaurantDao.getFilteredCount(name, category, shift, zone);
    }

    private Restaurant getRecommendedOfLoggedUser() {
        List<Restaurant> restaurantFavoriteList = restaurantDao.getTopTenByFavoriteOfUser(securityService.getCurrentUser().orElseThrow(IllegalStateException::new).getId());
        List<Restaurant> restaurantReservedList = restaurantDao.getTopTenByReservationsOfUser(securityService.getCurrentUser().orElseThrow(IllegalStateException::new).getId());
        HashMap<Zone, Integer> zoneIntegerHashMap = new HashMap<>();
        HashMap<Category, Integer> categoryIntegerHashMap = new HashMap<>();
        for (Restaurant favRestaurant : restaurantFavoriteList) {
            zoneIntegerHashMap.put(favRestaurant.getZone(), zoneIntegerHashMap.getOrDefault(favRestaurant.getZone(), 0) + 1);
            for (Category category : categoryService.getByRestaurantId(favRestaurant.getId())) {
                categoryIntegerHashMap.put(category, categoryIntegerHashMap.getOrDefault(category, 0) + 1);
            }
        }
        for (Restaurant resRestaurant : restaurantReservedList) {
            zoneIntegerHashMap.put(resRestaurant.getZone(), zoneIntegerHashMap.getOrDefault(resRestaurant.getZone(), 0) + 1);
            for (Category category : categoryService.getByRestaurantId(resRestaurant.getId())) {
                categoryIntegerHashMap.put(category, categoryIntegerHashMap.getOrDefault(category, 0) + 1);
            }
        }
        List<Restaurant> randomList = new ArrayList<>();

        // TODO

        return getRecommended();
    }

    private Restaurant getRecommended() {
        List<Restaurant> restaurantFavoriteList = restaurantDao.getTopTenByFavorite();
        List<Restaurant> restaurantReservedList = restaurantDao.getTopTenByReservations();
        List<Restaurant> randomList = new ArrayList<>();
        for (Restaurant favRestaurant : restaurantFavoriteList) {
            for (Restaurant resRestaurant : restaurantReservedList) {
                if (favRestaurant.getId() == resRestaurant.getId()) {
                     randomList.add(favRestaurant);
                }
            }
        }
        Random random = new Random();
        if (!randomList.isEmpty()) return randomList.get(random.nextInt(randomList.size()));
        if (!restaurantFavoriteList.isEmpty()) return restaurantFavoriteList.stream().findFirst().orElseThrow(IllegalStateException::new);
        if (!restaurantReservedList.isEmpty()) return restaurantReservedList.stream().findFirst().orElseThrow(IllegalStateException::new);
        return restaurantDao.getAll(1).stream().findFirst().orElseThrow(IllegalStateException::new);
    }

    @Override
    public Restaurant getRecommendedRestaurant(boolean isDiner) {
        if (isDiner) {
            return getRecommendedOfLoggedUser();
        } else {
            return getRecommended();
        }
    }
}
