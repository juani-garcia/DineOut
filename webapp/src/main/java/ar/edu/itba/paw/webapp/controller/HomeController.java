package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Category;
import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.model.Zone;
import ar.edu.itba.paw.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    protected AuthenticationManager authenticationManager;


    @RequestMapping(value = "/")
    public ModelAndView webapp() {
        final ModelAndView mav = new ModelAndView("home/index");
        mav.addObject("categories", Category.values());
        mav.addObject("zones", Zone.values());
        mav.addObject("shifts", Shift.values());
        return mav;
    }

    @RequestMapping(value = "/restaurants")
    public ModelAndView webapp(
            @RequestParam(name = "page", defaultValue = "1") final int page,
            @RequestParam(name = "name", defaultValue = "") final String name,
            @RequestParam(name = "byItem", defaultValue = "false") final boolean byItem,
            @RequestParam(name = "category", defaultValue = "-1") final int category,
            @RequestParam(name = "zone", defaultValue = "-1") final int zone,
            @RequestParam(name = "shift", defaultValue = "-1") final int shift) {
        PagedQuery<Restaurant> restaurants = restaurantService.filter(page, name, byItem, category, shift, zone);
        if (page != 1 && restaurants.getPageCount() < page)
            return new ModelAndView("redirect:/restaurants" + "?page=" + restaurants.getPageCount() +
                    "&name=" + name + "&category=" + category + "&zone=" + zone + "&shift=" + shift + "&byItem=" + byItem);

        final ModelAndView mav = new ModelAndView("home/restaurants");
        mav.addObject("categories", Category.values());
        mav.addObject("zones", Zone.values());
        mav.addObject("shifts", Shift.values());
        mav.addObject("restaurants", restaurants);
        return mav;
    }

}
