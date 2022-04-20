package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.form.ReservationForm;
import ar.edu.itba.paw.webapp.form.RestaurantForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RestaurantDetailController {

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping("/register_restaurant")
    public ModelAndView registerRestaurant(@ModelAttribute("restaurantForm") final RestaurantForm form) {
        return new ModelAndView("register/register_restaurant");
    }

    @RequestMapping(value = "/create_restaurant", method = {RequestMethod.POST})
    public ModelAndView create(@Valid @ModelAttribute("restaurantForm") final RestaurantForm form, final BindingResult errors) {

        if (errors.hasErrors()) {
            return registerRestaurant(form);
        }

        restaurantService.create(form.getName(), form.getAddress(), form.getEmail(), form.getDetail());
        return new ModelAndView("redirect:/profile");
    }

}

