package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exceptions.ForbiddenActionException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.model.MenuSection;
import ar.edu.itba.paw.persistence.MenuSectionDao;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
    public List<MenuSection> getByRestaurantId(final long restaurantId) {
        Restaurant restaurant = restaurantService.getById(restaurantId).orElseThrow(NotFoundException::new);
        return restaurant.getMenuSectionList();
    }

    @Transactional
    @Override
    public MenuSection create(final String name) {
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElseThrow(ForbiddenActionException::new);
        if (!restaurant.getUser().equals(user))
            throw new IllegalArgumentException("Cannot use someone else's restaurant");
        return restaurant.addMenuSection(name);
    }

    @Transactional
    @Override
    public void delete(final long menuSectionId) {
        MenuSection menuSection = validateSection(menuSectionId);
        menuSectionDao.delete(menuSectionId);
    }

    @Transactional
    @Override
    public void updateName(final long sectionId, final String newName) {
        MenuSection menuSection = validateSection(sectionId);
        menuSection.setName(newName);
    }

    private void move(final long sectionId, boolean moveUp) {
        MenuSection menuSection = validateSection(sectionId);
        List<MenuSection> menuSectionList = menuSection.getRestaurant().getMenuSectionList();
        final int index = menuSectionList.indexOf(menuSection);

        if ((moveUp && index == 0) || (!moveUp && index == menuSectionList.size() - 1))
            throw new IllegalArgumentException("Cannot move this section");

        Collections.swap(menuSectionList, index, index + (moveUp ? -1 : 1));
    }

    @Transactional
    @Override
    public void moveUp(final long menuSectionId) {
        move(menuSectionId, true);
    }

    @Transactional
    @Override
    public void moveDown(final long menuSectionId) {
        move(menuSectionId, false);
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
