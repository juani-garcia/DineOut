package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserRole;
import ar.edu.itba.paw.model.UserToRole;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.UserRoleService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.service.UserToRoleService;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserToRoleService userToRoleService;

    @Autowired
    private SecurityController securityController;

    private final HashMap<String, String> roles = new HashMap<String, String>() {
        {
            put("RESTAURANT", "Mi restaurante.");
            put("DINER", "Uso personal.");
        }
    };

    @RequestMapping(value = "/")
    public ModelAndView webapp() {
        final ModelAndView mav = new ModelAndView("home/index");
        return mav;
    }

    @RequestMapping(value = "/restaurants")
    public ModelAndView webapp(@RequestParam(name = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("home/restaurants");
        mav.addObject("restaurants", restaurantService.getAll(page));
        return mav;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerForm(@ModelAttribute("registerForm") final UserForm form) {
        final ModelAndView mav =new ModelAndView("register/register");
        List<String> roles = new ArrayList<>();
        roles.add(this.roles.get("RESTAURANT"));
        roles.add(this.roles.get("DINER"));
        mav.addObject("roleItems", roles);
        return mav;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return registerForm(form);
        }

        final User user = userService.create(form.getUsername(), form.getPassword(), form.getFirstName(), form.getLastName());

        if (form.getRole().equals(this.roles.get("RESTAURANT"))) {
            Optional<UserRole> userRole = userRoleService.getByRoleName("RESTAURANT");
            if (!userRole.isPresent()) throw new IllegalStateException("El rol RESTAURANT no esta presente en la bbdd");
            userToRoleService.create(user.getId(), userRole.get().getId());
        } else {
            Optional<UserRole> userRole = userRoleService.getByRoleName("DINER");
            if (!userRole.isPresent()) throw new IllegalStateException("El rol DINER no esta presente en la bbdd");
            userToRoleService.create(user.getId(), userRole.get().getId());
        }

        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("home/login");
    }

    @RequestMapping("/profile")
    public ModelAndView profile() {
        final ModelAndView mav = new ModelAndView("home/profile");
        Optional<User> loggedInUser = userService.getByUsername(securityController.getCurrentUserName());
        if (!loggedInUser.isPresent()) throw new IllegalStateException("Current user is not valid");
        mav.addObject("user", loggedInUser.get());
        return mav;
    }

}
