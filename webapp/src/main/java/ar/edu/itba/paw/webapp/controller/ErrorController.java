package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET})
    public ModelAndView renderErrorPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/error/error_page");
        String titleMessage, hintMessage;
        Integer code = getErrorCode(request);
        switch (code) {
            case 400: {
                titleMessage = "error.400.title";
                hintMessage = "error.400.hint";
                break;
            }
            case 401: {
                titleMessage = "error.401.title";
                hintMessage = "error.401.hint";
                break;
            }
            case 403: {
                titleMessage = "error.403.title";
                hintMessage = "error.403.hint";
                break;
            }
            case 404: {
                titleMessage = "error.404.title";
                hintMessage = "error.404.hint";
                break;
            }
            case 500: {
                titleMessage = "error.500.title";
                hintMessage = "error.500.hint";
                break;
            }
            default: {
                titleMessage = "error.unkown.title";
                hintMessage = "error.unkown.hint";
                break;
            }
        }
        mav.addObject("code", code);
        mav.addObject("title", titleMessage);
        mav.addObject("hint", hintMessage);
        return mav;
    }

    private static Integer getErrorCode(HttpServletRequest request) {
        return (Integer) request.getAttribute("javax.servlet.error.status_code");
    }

}
