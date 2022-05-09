package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such restaurant")
    public ModelAndView restaurantNotFound(){
        ModelAndView mav = new ModelAndView("error/error_page");
        mav.addObject("title", "error.404.title");
        mav.addObject("hint", "error.404.hint");
        mav.addObject("code", 404);
        return mav;
    }

    @ExceptionHandler(InvalidPageException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid page number")
    public ModelAndView invalidPage(){
        System.out.println("**************************************");
        ModelAndView mav = new ModelAndView("error/error_page");
        mav.addObject("title", "error.400.title");
        mav.addObject("hint", "error.400.hint");
        mav.addObject("code", 400);
        return mav;
    }

    @ExceptionHandler(UnauthenticatedUserException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthenticated user request")
    public ModelAndView unauthenticatedUserRequest(){
        System.out.println("**************************************");
        ModelAndView mav = new ModelAndView("error/error_page");
        mav.addObject("title", "error.401.title");
        mav.addObject("hint", "error.401.hint");
        mav.addObject("code", 401);
        return mav;
    }

    @ExceptionHandler(ForbiddenActionException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Forbidden action")
    public ModelAndView forbiddenAction(){
        System.out.println("**************************************");
        ModelAndView mav = new ModelAndView("error/error_page");
        mav.addObject("title", "error.403.title");
        mav.addObject("hint", "error.403.hint");
        mav.addObject("code", 403);
        return mav;
    }


}
