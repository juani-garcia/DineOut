package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistence.Restaurant;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.UserRole;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    private UserRoleService userRoleService;

    @Autowired
    private UserToRoleService userToRoleService;


    @Autowired
    private SecurityService securityService;

    private final HashMap<String, String> roles = new HashMap<String, String>() {
        {
            put("RESTAURANT", "Mi restaurante.");
            put("DINER", "Uso personal.");
        }
    };

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
        final ModelAndView mav = new ModelAndView("register/register");
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

        User user = userService.create(form.getUsername(), form.getPassword(), form.getFirstName(), form.getLastName());

        if (form.getRole().equals(this.roles.get("RESTAURANT"))) {  // TODO: move this logic into service
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
        User user = securityService.getCurrentUser();
        if (user == null) throw new IllegalStateException("Current user is not valid");
        if (userService.isRestaurant(user.getId())) { // TODO
            return new ModelAndView("redirect:/restaurant");
        }
        return new ModelAndView("redirect:/diner/profile");
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("user", securityService.getCurrentUser());
    }

}
