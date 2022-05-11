package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such restaurant")
    public ModelAndView restaurantNotFound(NotFoundException ex) {
        LOGGER.warn(ex.getMessage());
        return new ModelAndView("forward:/404");
    }

    @ExceptionHandler(InvalidPageException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid page number")
    public ModelAndView invalidPage(InvalidPageException ex) {
        LOGGER.warn(ex.getMessage());
        return new ModelAndView("forward:/400");
    }

    @ExceptionHandler(UnauthenticatedUserException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthenticated user request")
    public ModelAndView unauthenticatedUserRequest(UnauthenticatedUserException ex) {
        LOGGER.warn(ex.getMessage());
        return new ModelAndView("forward:/401");
    }

    @ExceptionHandler(ForbiddenActionException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Forbidden action")
    public ModelAndView forbiddenAction(ForbiddenActionException ex) {
        LOGGER.warn(ex.getMessage());
        return new ModelAndView("forward:/403");
    }

}
