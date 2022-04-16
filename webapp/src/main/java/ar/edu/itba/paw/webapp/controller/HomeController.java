package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(value = "/")
    public ModelAndView webapp(@RequestParam(name = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("home/index");
        mav.addObject("restaurants", restaurantService.getAll(page));
        return mav;
    }

}
