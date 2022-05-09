package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.persistence.MenuSection;
import ar.edu.itba.paw.persistence.Restaurant;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private MenuSectionService menuSectionService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private ErrorController errorController;

    @RequestMapping("/menu/{resID}")
    public ModelAndView menu(@PathVariable final long resID) {
        final ModelAndView mav = new ModelAndView("reservation/menu");
        Restaurant restaurant = restaurantService.getById(resID).orElseThrow(NotFoundException::new);
        List<MenuSection> menuSectionList = menuSectionService.getByRestaurantId(resID);
        for (MenuSection section : menuSectionList) {
            section.setMenuItemList(menuItemService.getBySectionId(section.getId()));
        }
        mav.addObject("sections", menuSectionList);
        mav.addObject("restaurant", restaurant);
        return mav;
    }
}
