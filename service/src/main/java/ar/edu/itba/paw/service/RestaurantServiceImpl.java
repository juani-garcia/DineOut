package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.InvalidPageException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import ar.edu.itba.paw.persistence.RestaurantDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    @Override
    public Optional<Restaurant> getById(long id) {
        return restaurantDao.getById(id);
    }

    @Override
    public Optional<Restaurant> getByMail(String mail) {
        return restaurantDao.getByMail(mail);
    }

    @Override
    public PagedQuery<Restaurant> filter(FilterParams params) {
        if (params.getPage() <= 0) throw new InvalidPageException();

        if(params.getRecommendedFor() != null) {
            PagedQuery<Restaurant> result = getRecommendedOfUser(params.getRecommendedFor());
            if (!result.getContent().isEmpty()) return result;
            params = new FilterParams();
        }

        return restaurantDao.filter(params);
    }

    @Transactional
    @Override
    public Restaurant create(String name, byte[] image, String address, String mail, String detail, Zone zone, final Float lat, final Float lng, final List<Category> categories, final List<Shift> shifts) {
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new); // TODO: Catch this exception with a bad request or similar
        if (getByUserID(user.getId()).isPresent()) throw new IllegalStateException();
        Image restaurantImage = null;
        if (image != null && image.length > 0) {
            restaurantImage = imageService.create(image);
        }
        Restaurant restaurant = restaurantDao.create(user, name, restaurantImage, address, mail, detail, zone, lat, lng);
        restaurant.setCategories(categories);
        restaurant.setShifts(shifts);
        return restaurant;
    }

    @Transactional
    @Override
    public void updateCurrentRestaurant(String name, String address, String mail, String detail, Zone zone, final Float lat, final Float lng, List<Category> categories, List<Shift> shifts, byte[] imageBytes) {
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
        restaurant.setCategories(categories);
        restaurant.setShifts(shifts);
    }

    @Override
    public Optional<Restaurant> getByUserID(long id) {
        return restaurantDao.getByUserId(id);
    }

    private PagedQuery<Restaurant> getRecommendedOfUser(Long userId) {
        final User user = securityService.checkCurrentUser(userId);

        LOGGER.debug("Getting recommended for user {}", user.getId());

        List<Restaurant> restaurantFavoriteList = restaurantDao.getTopTenByFavoriteOfUser(userId);
        List<Restaurant> restaurantReservedList = restaurantDao.getTopTenByReservationsOfUser(user.getUsername());
        if (restaurantFavoriteList.isEmpty() && restaurantReservedList.isEmpty()) return PagedQuery.emptyPage();

        HashMap<Zone, Integer> zoneIntegerHashMap = new HashMap<>();
        HashMap<Category, Integer> categoryIntegerHashMap = new HashMap<>();
        for (Restaurant favRestaurant : restaurantFavoriteList) {
            zoneIntegerHashMap.putIfAbsent(favRestaurant.getZone(), 1);
            zoneIntegerHashMap.put(favRestaurant.getZone(), zoneIntegerHashMap.get(favRestaurant.getZone()) + 1);
            for (Category category : favRestaurant.getCategories()) {
                categoryIntegerHashMap.put(category, categoryIntegerHashMap.getOrDefault(category, 0) + 1);
            }
        }
        for (Restaurant resRestaurant : restaurantReservedList) {
            zoneIntegerHashMap.putIfAbsent(resRestaurant.getZone(), 1);
            zoneIntegerHashMap.put(resRestaurant.getZone(), zoneIntegerHashMap.getOrDefault(resRestaurant.getZone(), 0) + 1);
            for (Category category : resRestaurant.getCategories()) {
                categoryIntegerHashMap.put(category, categoryIntegerHashMap.getOrDefault(category, 0) + 1);
            }
        }

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

        final List<Restaurant> randomList = new ArrayList<>();
        if (favZone != null && favZone.getKey() != null) {
            randomList.addAll(restaurantDao.getTopTenByZone(favZone.getKey()));
        }
        if (favCategory != null && favCategory.getKey() != null) {
            randomList.addAll(restaurantDao.getTopTenByCategory(favCategory.getKey()));
        }

        if (randomList.isEmpty()) return PagedQuery.emptyPage();
        return new PagedQuery<>(randomList, 1L, 1L);
    }

    @Override
    public Optional<Restaurant> getOfLoggedUser() {
        Optional<User> user = securityService.getCurrentUser();
        if (user.isPresent()) return getByUserID(user.get().getId());
        return Optional.empty();
    }

    @Transactional
    @Override
    public void updateRestaurantImage(final long id, final byte[] image) {
        Restaurant restaurant = restaurantDao.getById(id).orElseThrow(NotFoundException::new); // TODO: Customize
        Image oldImage = restaurant.getImage();
        if (image != null && image.length > 0) { // There is new image
            if (oldImage == null) { // There is no old image
                LOGGER.debug("Creating image for restaurant {}", id);
                restaurant.setImage(imageService.create(image));
                LOGGER.debug("New image id: {}", restaurant.getImage().getId());
            } else {
                LOGGER.debug("Updating image for restaurant {}", id);
                imageService.edit(oldImage.getId(), image);
            }
        } else { // No new image
            if (oldImage != null) {
                LOGGER.debug("Deleting image for restaurant {}", id);
                restaurant.setImage(null);
                imageService.delete(oldImage.getId());
            }
        }
    }
}
