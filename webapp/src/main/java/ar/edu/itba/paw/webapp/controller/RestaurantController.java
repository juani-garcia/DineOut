package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.MenuSection;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.MenuSectionService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.MenuItemForm;
import ar.edu.itba.paw.webapp.form.MenuSectionForm;
import ar.edu.itba.paw.webapp.form.RestaurantForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuSectionService menuSectionService;

    @RequestMapping("")
    public ModelAndView restaurantProfile(Principal principal) {
        final ModelAndView mav = new ModelAndView("restaurant/profile");

        User user = userService.getByUsername(principal.getName()).get();
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElse(null);
        mav.addObject("restaurant", restaurant);
        return mav;
    }

        return new ModelAndView("register/register_restaurant");
    }

    @RequestMapping(value = "/create_restaurant", method = {RequestMethod.POST})
    public ModelAndView create(@Valid @ModelAttribute("restaurantForm") final RestaurantForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return registerRestaurant(form);
        }

        restaurantService.create(userID, form.getName(), form.getAddress(), form.getEmail(), form.getDetail());
        return new ModelAndView("redirect:/profile");
    }

    private long verifyCreateRestaurant() {  // TODO: Clean up this.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();   // TODO: Improve how we ask for users
        String username = auth.getName();
        Optional<User> user = userService.getByUsername(username);
        if (!user.isPresent()) throw new IllegalStateException("Tenes que estar loggeado para realizar esta accion.");
        long userID = user.get().getId();
        if (restaurantService.getByUserID(userID).isPresent()) throw new IllegalStateException("Tenes que estar loggeado para realizar esta accion.");
        return userID;
    }

}

