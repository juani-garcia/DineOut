package ar.edu.itba.paw.webapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @RequestMapping(value = "/404", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView error404() {
        ModelAndView mav = new ModelAndView("error/error_page");
        mav.addObject("title", "error.404.title");
        mav.addObject("hint", "error.404.hint");
        mav.addObject("code", 404);
        return mav;
    }

    @RequestMapping(value = "/400", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView error400() {
        ModelAndView mav = new ModelAndView("error/error_page");
        mav.addObject("title", "error.400.title");
        mav.addObject("hint", "error.400.hint");
        mav.addObject("code", 400);
        return mav;
    }

    @RequestMapping(value = "/403", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView error403() {
        ModelAndView mav = new ModelAndView("error/error_page");
        mav.addObject("title", "error.403.title");
        mav.addObject("hint", "error.403.hint");
        mav.addObject("code", 403);
        return mav;
    }

    @RequestMapping(value = "/401", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView error401() {
        ModelAndView mav = new ModelAndView("error/error_page");
        mav.addObject("title", "error.400.title");
        mav.addObject("hint", "error.400.hint");
        mav.addObject("code", 401);
        return mav;
    }

    @RequestMapping(value = "/405", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView error405() {
        ModelAndView mav = new ModelAndView("error/error_page");
        mav.addObject("title", "error.405.title");
        mav.addObject("hint", "error.405.hint");
        mav.addObject("code", 405);
        return mav;
    }

    @RequestMapping(value = "/500", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView error500() {
        ModelAndView mav = new ModelAndView("error/error_page");
        mav.addObject("title", "error.500.title");
        mav.addObject("hint", "error.500.hint");
        mav.addObject("code", 500);
        return mav;
    }

}
