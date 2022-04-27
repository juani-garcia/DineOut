package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class SecurityController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String getCurrentUserName() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication.getName();
    }

    @RequestMapping(value = "/userid", method = RequestMethod.GET)
    @ResponseBody
    public Long getCurrentUserId() {
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName = authentication.getName();
        Optional<User> optionalUser = userService.getByUsername(userName);
        if (!optionalUser.isPresent()) return null;
        return optionalUser.get().getId();
    }

    @RequestMapping(value = "/first_name", method = RequestMethod.GET)
    @ResponseBody
    public String getCurrentFirstName() {
        Authentication authentication = authenticationFacade.getAuthentication();
        String userName = authentication.getName();
        Optional<User> optionalUser = userService.getByUsername(userName);
        return optionalUser.map(User::getFirstName).orElse(null);
    }
}