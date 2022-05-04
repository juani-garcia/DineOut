package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.MenuSection;
import ar.edu.itba.paw.persistence.MenuSectionDao;
import ar.edu.itba.paw.persistence.Restaurant;
import ar.edu.itba.paw.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
public class MenuSectionServiceImpl implements MenuSectionService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuSectionDao menuSectionDao;

    @Override
    public Optional<MenuSection> getById(final long sectionId) {
        return menuSectionDao.getById(sectionId);
    }

    @Override
    public List<MenuSection> getByRestaurantId(long restaurantId) {
        return menuSectionDao.getByRestaurantId(restaurantId);
    }

    @Override
    public MenuSection create(final long restaurantId, final String name) {
        return menuSectionDao.create(restaurantId, name);
    }

    @Override
    public boolean delete(final long sectionId) {
        MenuSection menuSection = getById(sectionId).orElseThrow( () -> new RuntimeException("Invalid section"));
        Restaurant restaurant = restaurantService.getById(menuSection.getRestaurantId()).get();
        if (restaurant.getUserID() != restaurant.getUserID())
            throw new RuntimeException("Invalid permissions");
        return menuSectionDao.delete(sectionId);
    }

    @Override
    public boolean edit(long sectionId, String name, long restaurantId, long ordering) {
        return menuSectionDao.edit(sectionId, name, restaurantId, ordering);
    }

    @Override
    public boolean moveUp(final long sectionId) {
        User user = securityService.getCurrentUser();
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElseThrow(() -> new RuntimeException("Invalid user"));
        List<MenuSection> menuSections = getByRestaurantId(restaurant.getId());
        Optional<MenuSection> optionalMenuSection = getById(sectionId);
        if (! optionalMenuSection.isPresent() || optionalMenuSection.get().getOrdering() <= 1 || ! menuSections.stream().anyMatch( (section) -> section.getId() ==  sectionId)) {
            throw new RuntimeException("Invalid sectionId");
        }
        MenuSection menuSection = optionalMenuSection.get();
        return edit(sectionId, menuSection.getName(), menuSection.getRestaurantId(), menuSection.getOrdering() - 1);
    }

    @Override
    public boolean moveDown(long sectionId) {
        User user = securityService.getCurrentUser();
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElseThrow(() -> new RuntimeException("Invalid user"));
        List<MenuSection> menuSections = getByRestaurantId(restaurant.getId());
        Optional<MenuSection> optionalMenuSection = getById(sectionId);
        if (! optionalMenuSection.isPresent() || optionalMenuSection.get().getOrdering() >= menuSections.size() || ! menuSections.stream().anyMatch( (section) -> section.getId() ==  sectionId)) {
            throw new RuntimeException("Invalid sectionId");
        }
        MenuSection menuSection = optionalMenuSection.get();
        return edit(sectionId, menuSection.getName(), menuSection.getRestaurantId(), menuSection.getOrdering() + 1);

    }
}
