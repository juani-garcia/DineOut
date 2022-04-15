package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private RestaurantService restaurantService;

    @ExceptionHandler(RestaurantNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchRestaurant() {
        return new ModelAndView("error/restaurant_not_found");
    }

    @RequestMapping(value = "/")
    public ModelAndView webapp(@RequestParam(name = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("home/index");
        mav.addObject("restaurants", restaurantService.getAll(page));
        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("home/login");
    }

}
