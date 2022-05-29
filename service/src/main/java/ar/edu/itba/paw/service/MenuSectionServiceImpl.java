package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.model.MenuSection;
import ar.edu.itba.paw.persistence.MenuSectionDao;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MenuSectionServiceImpl implements MenuSectionService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuSectionDao menuSectionDao;

    @Autowired
    private MenuItemService menuItemService;

    @Override
    public Optional<MenuSection> getById(final long sectionId) {
        return menuSectionDao.getById(sectionId);
    }

    @Override
    public List<MenuSection> getByRestaurantId(long restaurantId) {
        List<MenuSection> menuSectionList = menuSectionDao.getByRestaurantId(restaurantId);
        menuSectionList.forEach((section) -> section.setMenuItemList(menuItemService.getBySectionId(section.getId())));
        return menuSectionList;
    }

    @Override
    public MenuSection create(final long restaurantId, final String name) {
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        Restaurant restaurant = restaurantService.getById(restaurantId).orElseThrow(IllegalArgumentException::new);
        if (!Objects.equals(user.getId(), restaurant.getUser().getId()))
            throw new IllegalArgumentException("Cannot use someone else's restaurant");
        return menuSectionDao.create(restaurantId, name);
    }

    @Override
    public boolean delete(final long sectionId) {
        MenuSection menuSection = validateSection(sectionId);
        return menuSectionDao.delete(sectionId);
    }

    @Override
    public boolean edit(long sectionId, String name, long restaurantId, long ordering) {
        return menuSectionDao.edit(sectionId, name, restaurantId, ordering);
    }

    @Override
    public boolean updateName(final long sectionId, final String newName) {
        MenuSection menuSection = validateSection(sectionId);
        return edit(sectionId, newName, menuSection.getRestaurant().getId(), menuSection.getOrdering());
    }

    private boolean move(final long sectionId, boolean moveUp) {
        MenuSection menuSection = validateSection(sectionId);
        List<MenuSection> menuSections = getByRestaurantId(menuSection.getRestaurant().getId()); // TODO: Migrate
        if ((moveUp ? menuSection.getOrdering() <= 1 : menuSection.getOrdering() >= menuSections.size())) {
            throw new IllegalArgumentException("Cannot move this section");
        }
        // TODO> Migrate to model modification
        return edit(sectionId, menuSection.getName(), menuSection.getRestaurant().getId(), menuSection.getOrdering() + (moveUp ? -1 : 1));
    }

    @Override
    public boolean moveUp(final long sectionId) {
        return move(sectionId, true);
    }

    @Override
    public boolean moveDown(long sectionId) {
        return move(sectionId, false);
    }

    protected MenuSection validateSection(final long sectionId) {
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        MenuSection menuSection = getById(sectionId).orElseThrow(IllegalArgumentException::new);
        Restaurant restaurant = menuSection.getRestaurant();
        if (!Objects.equals(user.getId(), restaurant.getUser().getId()))
            throw new IllegalArgumentException("Cannot use someone else's restaurant");
        return menuSection;
    }

}
