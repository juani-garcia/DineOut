package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String getCurrentUserName() {
        User user = securityService.getCurrentUser();
        return user == null? null : user.getUsername();
    }

    @RequestMapping(value = "/userid", method = RequestMethod.GET)
    @ResponseBody
    public Long getCurrentUserId() {
        User user = securityService.getCurrentUser();
        return user == null? null : user.getId();
    }

    @RequestMapping(value = "/first_name", method = RequestMethod.GET)
    @ResponseBody
    public String getCurrentFirstName() {
        User user = securityService.getCurrentUser();
        return user == null? null : user.getFirstName();
    }
}