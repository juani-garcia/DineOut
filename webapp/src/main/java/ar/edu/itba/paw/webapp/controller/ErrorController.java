package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exceptions.DuplicatedUsernameException;
import ar.edu.itba.paw.model.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.UserForm;
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

    @ExceptionHandler(DuplicatedUsernameException.class)
    public ModelAndView duplicatedUsername(DuplicatedUsernameException ex) {
        ModelAndView mav = new ModelAndView("register/register");
        UserForm form = new UserForm();
        form.setUsername(ex.getUsername());
        form.setPassword(ex.getPassword());
        form.setConfirmPassword(ex.getPassword());
        form.setFirstName(ex.getFirstName());
        form.setLastName(ex.getLastName());
        mav.addObject("registerForm", form);
        mav.addObject("duplicatedUsername", true);
        return mav;
    }
}
