package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private ImageService imageService;

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

    @Transactional
    @Override
    public Restaurant create(String name, byte[] image, String address, String mail, String detail, Zone zone, final List<Long> categories, final List<Long> shifts) {
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        if (getByUserID(user.getId()).isPresent())
            throw new IllegalStateException();
        Image restaurantImage = null;
        if (image != null && image.length > 0) {
            restaurantImage = imageService.create(image);
        }
        Restaurant restaurant = restaurantDao.create(user.getId(), name, (restaurantImage != null ? restaurantImage.getId() : null), address, mail, detail, zone);
        for (Long categoryId : categories) {
            Category category = Category.getById(categoryId);
            categoryService.add(restaurant.getId(), category);
        }
        restaurant.setShifts(shifts.stream().map(Shift::getById).collect(Collectors.toList()));
        return restaurant;
    }

    @Override
    public boolean updateCurrentRestaurant(String name, String address, String mail, String detail, Zone zone, List<Long> categories, List<Long> shifts, byte[] imageBytes) {
        User user = securityService.getCurrentUser().orElseThrow(IllegalStateException::new);
        Restaurant restaurant = restaurantDao.getByUserId(user.getId()).orElseThrow(IllegalStateException::new);
        Long imageId = restaurant.getImageId();
        if (imageBytes.length > 0) {
            if (restaurant.getImageId() != null) {
                imageService.delete(restaurant.getImageId());
            }
            imageId = imageService.create(imageBytes).getId();
        }
        restaurant.setShifts(shifts.stream().map(Shift::getById).collect(Collectors.toList()));
        return restaurantDao.update(restaurant.getId(), name, address, mail, detail, zone, imageId);
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
        List<Restaurant> restaurantReservedList = restaurantDao.getTopTenByReservationsOfUser(securityService.getCurrentUser().orElseThrow(IllegalStateException::new).getUsername());
        if (restaurantFavoriteList.isEmpty() && restaurantReservedList.isEmpty()) return getRecommended();

        HashMap<Zone, Integer> zoneIntegerHashMap = new HashMap<>();
        HashMap<Category, Integer> categoryIntegerHashMap = new HashMap<>();
        for (
                Restaurant favRestaurant : restaurantFavoriteList) {
            zoneIntegerHashMap.put(favRestaurant.getZone(), zoneIntegerHashMap.getOrDefault(favRestaurant.getZone(), 0) + 1);
            for (Category category : categoryService.getByRestaurantId(favRestaurant.getId())) {
                categoryIntegerHashMap.put(category, categoryIntegerHashMap.getOrDefault(category, 0) + 1);
            }
        }
        for (
                Restaurant resRestaurant : restaurantReservedList) {
            zoneIntegerHashMap.put(resRestaurant.getZone(), zoneIntegerHashMap.getOrDefault(resRestaurant.getZone(), 0) + 1);
            for (Category category : categoryService.getByRestaurantId(resRestaurant.getId())) {
                categoryIntegerHashMap.put(category, categoryIntegerHashMap.getOrDefault(category, 0) + 1);
            }
        }

        List<Restaurant> randomList = new ArrayList<>();

        Map.Entry<Category, Integer> favCategory = categoryIntegerHashMap.entrySet().stream().findFirst().orElse(null);
        Map.Entry<Zone, Integer> favZone = zoneIntegerHashMap.entrySet().stream().findFirst().orElse(null);

        for (Map.Entry<Zone, Integer> zoneIntegerEntry : zoneIntegerHashMap.entrySet()) {
            if (favZone.getValue() < zoneIntegerEntry.getValue()) {
                favZone = zoneIntegerEntry;
            }
            for (Map.Entry<Category, Integer> categoryIntegerEntry : categoryIntegerHashMap.entrySet()) {
                if (favCategory.getValue() < categoryIntegerEntry.getValue()) {
                    favCategory = categoryIntegerEntry;
                }
            }
        }

        if (favZone != null) {
            randomList.addAll(restaurantDao.getTopTenByZone(favZone.getKey()));
        }
        if (favCategory != null) {
            randomList.addAll(restaurantDao.getTopTenByCategory(favCategory.getKey()));
        }

        if (randomList.isEmpty()) return getRecommended();
        Random random = new Random();
        return randomList.get(random.nextInt(randomList.size()));
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
        if (!restaurantFavoriteList.isEmpty())
            return restaurantFavoriteList.stream().findFirst().orElseThrow(IllegalStateException::new);
        if (!restaurantReservedList.isEmpty())
            return restaurantReservedList.stream().findFirst().orElseThrow(IllegalStateException::new);
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

    @Override
    public long getFilteredPagesCount(String name, int categoryId, int shiftId, int zoneId) {
        Category category = Category.getById(categoryId);
        Zone zone = Zone.getById(zoneId);
        Shift shift = Shift.getById(shiftId);

        return restaurantDao.getFilteredPagesCount(name, category, shift, zone);
    }

    @Override
    public Optional<Restaurant> getOfLoggedUser() {
        Optional<User> user = securityService.getCurrentUser();
        if (user.isPresent()) return getByUserID(user.get().getId());
        return Optional.empty();
    }
}
