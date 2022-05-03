package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Category;
import ar.edu.itba.paw.model.Zone;
import ar.edu.itba.paw.model.exceptions.DuplicatedMailException;
import ar.edu.itba.paw.model.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.RestaurantForm;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(RestaurantNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchRestaurant() {
        return new ModelAndView("error/restaurant_not_found");
    }

    @RequestMapping("/403")
    public ModelAndView accessDenied() {
        return new ModelAndView("error/errorPage403");
    }

    @ExceptionHandler(DuplicatedMailException.class)
    public ModelAndView duplicatedMail(DuplicatedMailException ex) {
        ModelAndView mav = new ModelAndView("register/register_restaurant");
        RestaurantForm form = new RestaurantForm();
        form.setAddress(ex.getAddress());
        form.setCategories(ex.getCategories());
        form.setEmail(ex.getMail());
        form.setZone(ex.getZone().getName());
        form.setName(ex.getName());
        form.setDetail(ex.getDetail());
        mav.addObject("restaurantForm", form);
        mav.addObject("duplicatedMail", true);
        mav.addObject("zones", Zone.values());
        mav.addObject("categoryList", Category.values());
        return mav;
    }
}
