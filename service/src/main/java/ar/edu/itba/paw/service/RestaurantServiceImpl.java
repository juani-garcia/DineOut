package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.InvalidPageException;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.persistence.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private SecurityService securityService;

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
    public PagedQuery<Restaurant> filter(int page, String name, Category category, Shift shift, Zone zone) {
        if (page <= 0) throw new InvalidPageException();
        return restaurantDao.filter(page, name, category, shift, zone);
    }

    @Transactional
    @Override
    public Restaurant create(String name, byte[] image, String address, String mail, String detail, Zone zone, final Float lat, final Float lng, final List<Long> categories, final List<Long> shifts) {
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new); // TODO: Catch this exception with a bad request or similar
        if (getByUserID(user.getId()).isPresent())
            throw new IllegalStateException();
        Image restaurantImage = null;
        if (image != null && image.length > 0) {
            restaurantImage = imageService.create(image);
        }
        Restaurant restaurant = restaurantDao.create(user, name, restaurantImage, address, mail, detail, zone, lat, lng);
        restaurant.setCategories(categories.stream().map(Category::getById).collect(Collectors.toList()));
        restaurant.setShifts(shifts.stream().map(Shift::getById).collect(Collectors.toList()));
        return restaurant;
    }

    @Transactional
    @Override
    public void updateCurrentRestaurant(String name, String address, String mail, String detail, Zone zone, final Float lat, final Float lng, List<Long> categories, List<Long> shifts, byte[] imageBytes) {
        User user = securityService.getCurrentUser().orElseThrow(IllegalStateException::new);
        Restaurant restaurant = restaurantDao.getByUserId(user.getId()).orElseThrow(IllegalStateException::new);
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setMail(mail);
        restaurant.setDetail(detail);
        restaurant.setZone(zone);
        restaurant.setLat(lat);
        restaurant.setLng(lng);
        Image image = restaurant.getImage();
        if (image != null) {
            if (imageBytes != null && imageBytes.length > 0) {
                imageService.edit(image.getId(), imageBytes);
            } else {
                restaurant.setImage(null);
                imageService.delete(image.getId());
            }
        } else {
            if (imageBytes != null && imageBytes.length > 0) {
                restaurant.setImage(imageService.create(imageBytes));
            }
        }
        restaurant.setCategories(categories.stream().map(Category::getById).collect(Collectors.toList()));
        restaurant.setShifts(shifts.stream().map(Shift::getById).collect(Collectors.toList()));
    }

    @Override
    public Optional<Restaurant> getByUserID(long id) {
        return restaurantDao.getByUserId(id);
    }

    private Restaurant getRecommendedOfLoggedUser() {
        List<Restaurant> restaurantFavoriteList = restaurantDao.getTopTenByFavoriteOfUser(securityService.getCurrentUser().orElseThrow(IllegalStateException::new).getId());
        List<Restaurant> restaurantReservedList = restaurantDao.getTopTenByReservationsOfUser(securityService.getCurrentUser().orElseThrow(IllegalStateException::new).getUsername());
        if (restaurantFavoriteList.isEmpty() && restaurantReservedList.isEmpty()) return getRecommended();

        HashMap<Zone, Integer> zoneIntegerHashMap = new HashMap<>();
        HashMap<Category, Integer> categoryIntegerHashMap = new HashMap<>();
        for (
                Restaurant favRestaurant : restaurantFavoriteList) {
            zoneIntegerHashMap.putIfAbsent(favRestaurant.getZone(), 1);
            zoneIntegerHashMap.put(favRestaurant.getZone(), zoneIntegerHashMap.get(favRestaurant.getZone()) + 1);
            for (Category category : favRestaurant.getCategories()) {
                categoryIntegerHashMap.put(category, categoryIntegerHashMap.getOrDefault(category, 0) + 1);
            }
        }
        for (
                Restaurant resRestaurant : restaurantReservedList) {
            zoneIntegerHashMap.putIfAbsent(resRestaurant.getZone(), 1);
            zoneIntegerHashMap.put(resRestaurant.getZone(), zoneIntegerHashMap.getOrDefault(resRestaurant.getZone(), 0) + 1);
            for (Category category : resRestaurant.getCategories()) {
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

        if (favZone != null && favZone.getKey() != null) {
            randomList.addAll(restaurantDao.getTopTenByZone(favZone.getKey()));
        }
        if (favCategory != null && favCategory.getKey() != null) {
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
        return restaurantDao.filter(1, null, null, null, null)
                .getContent().stream().findFirst().orElseThrow(IllegalStateException::new);
        // TODO: @JeroBrave Customize exception
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
    public Optional<Restaurant> getOfLoggedUser() {
        Optional<User> user = securityService.getCurrentUser();
        if (user.isPresent()) return getByUserID(user.get().getId());
        return Optional.empty();
    }
}
