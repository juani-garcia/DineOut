package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.MenuItem;
import ar.edu.itba.paw.model.MenuSection;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.MenuItemService;
import ar.edu.itba.paw.service.MenuSectionService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.MenuItemForm;
import ar.edu.itba.paw.webapp.form.MenuSectionForm;
import ar.edu.itba.paw.webapp.form.RestaurantForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuSectionService menuSectionService;

    @Autowired
    private MenuItemService menuItemService;

    @RequestMapping("")
    public ModelAndView restaurantProfile(Principal principal) {
        final ModelAndView mav = new ModelAndView("restaurant/profile");

        User user = userService.getByUsername(principal.getName()).get();
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElse(null);
        mav.addObject("restaurant", restaurant);


        List<MenuSection> menuSectionList = menuSectionService.getByRestaurantId(restaurant.getId());
        menuSectionList.forEach( (section) -> section.setMenuItemList(menuItemService.getBySectionId(section.getId())));
        mav.addObject("sections", menuSectionList);
        return mav;
    }

    @RequestMapping("/register")
    public ModelAndView restaurantForm(@ModelAttribute("restaurantForm") final RestaurantForm form) {
        return new ModelAndView("register/register_restaurant");
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public ModelAndView create(@Valid @ModelAttribute("restaurantForm") final RestaurantForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return restaurantForm(form);
        }

        return new ModelAndView("redirect:/restaurant");
    }

    @RequestMapping(value = "/section")
    public ModelAndView sectionForm(@ModelAttribute("sectionForm") final MenuSectionForm form) {
        return new ModelAndView("restaurant/section_form");
    }

    @RequestMapping(value = "/section", method = {RequestMethod.POST})
    public ModelAndView section(Principal principal, @Valid @ModelAttribute("sectionForm") final MenuSectionForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return sectionForm(form);
        }

        User user = userService.getByUsername(principal.getName()).get();
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElseThrow( () -> new RuntimeException("No hay restaurante"));
        MenuSection menuSection = menuSectionService.create(form.getName(), restaurant.getId(), form.getOrdering());
        return new ModelAndView("redirect:/restaurant");
    }

    @RequestMapping(value = "/item")
    public ModelAndView itemForm(Principal principal, @ModelAttribute("itemForm") final MenuItemForm form) {
        ModelAndView mav = new ModelAndView("restaurant/item_form");
        User user = userService.getByUsername(principal.getName()).get();
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElse(null);
        List<MenuSection> menuSectionList = menuSectionService.getByRestaurantId(restaurant.getId());
        mav.addObject("sections", menuSectionList);
        return mav;
    }

    @RequestMapping(value = "/item", method = {RequestMethod.POST})
    public ModelAndView item(Principal principal, @Valid @ModelAttribute("itemForm") final MenuItemForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return itemForm(principal, form);
        }

        User user = userService.getByUsername(principal.getName()).get();
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElseThrow( () -> new RuntimeException("No hay restaurante"));
        MenuItem menuItem = menuItemService.create(form.getName(), form.getDetail(), form.getPrice(), form.getMenuSectionId(), form.getOrdering(), null);
        return new ModelAndView("redirect:/restaurant");
    }

}

