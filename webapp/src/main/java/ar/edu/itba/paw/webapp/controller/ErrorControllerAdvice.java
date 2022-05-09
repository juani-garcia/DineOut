package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedReservationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(RestaurantNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such restaurant")
    public ModelAndView restaurantNotFound(){
        ModelAndView mav = new ModelAndView("error/error_page");
        mav.addObject("title", "error.404.title");
        mav.addObject("hint", "error.404.hint");
        mav.addObject("code", 404);
        return mav;
    }

    @ExceptionHandler(UnauthorizedReservationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Reservation does not belong to current user")
    public ModelAndView unauthorizedReservation(){
        System.out.println("**************************************");
        ModelAndView mav = new ModelAndView("error/error_page");
        mav.addObject("title", "error.401.title");
        mav.addObject("hint", "error.401.hint");
        mav.addObject("code", 401);
        return mav;
    }
}
