package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistence.Restaurant;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
@ControllerAdvice
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/")
    public ModelAndView webapp() {
        final ModelAndView mav = new ModelAndView("home/index");
        mav.addObject("categories", Category.values());
        mav.addObject("zones", Zone.values());
        mav.addObject("shifts", Shift.values());
        return mav;
    }

    @RequestMapping(value = "/restaurants")
    public ModelAndView webapp(
            @RequestParam(name = "page", defaultValue = "1") final int page,
            @RequestParam(name = "name", defaultValue = "") final String name,
            @RequestParam(name = "category", defaultValue = "-1") final int category,
            @RequestParam(name = "zone", defaultValue = "-1") final int zone,
            @RequestParam(name = "shift", defaultValue = "-1") final int shift) {
        final ModelAndView mav = new ModelAndView("home/restaurants");
        mav.addObject("categories", Category.values());
        mav.addObject("zones", Zone.values());
        mav.addObject("shifts", Shift.values());
        mav.addObject("pageSize", 3); // TODO: remove magin number for PAGE_SIZE getter from dao.
        mav.addObject("totalRestaurantCount", restaurantService.getFilteredCount(name, category, shift, zone));
        mav.addObject("restaurants", restaurantService.filter(page, name, category, shift, zone));
        return mav;
    }

    @RequestMapping(value = "/restaurant_picker")
    public ModelAndView restaurantPicker() {  // TODO: use tags and previous reservations to choose restaurant.
        final List<Restaurant> restaurantList = restaurantService.getAll(1);
        Random random = new Random();
        return new ModelAndView("redirect:/restaurant/view/" + restaurantList.get(random.nextInt(restaurantList.size())).getId());
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerForm(@ModelAttribute("registerForm") final UserForm form) {
        return new ModelAndView("register/register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors, HttpServletRequest request) {

        if (errors.hasErrors()) {
            return registerForm(form);
        }

        User user = userService.create(form.getUsername(), form.getPassword(), form.getFirstName(), form.getLastName(), form.getIsRestaurant());
        authenticateUserAndSetSession(request, form.getUsername(), form.getPassword());

        return new ModelAndView("redirect:/profile");
    }

    /* Retrived from : https://www.baeldung.com/spring-security-auto-login-user-after-registration#authManager*/
    private void authenticateUserAndSetSession(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("home/login");
    }

    @RequestMapping("/profile")
    public ModelAndView profile(HttpServletRequest request) {
        if (request.isUserInRole("RESTAURANT")) {
            return new ModelAndView("redirect:/restaurant");
        } else if (request.isUserInRole("DINER")) {
            return new ModelAndView("redirect:/diner/profile");
        }
        throw new BadCredentialsException("Logged user is neither a RESTAURANT or a DINER");
    }

    @ModelAttribute
    public void addUser(Model model) {
        model.addAttribute("user", securityService.getCurrentUser().orElse(null));
    }

}
