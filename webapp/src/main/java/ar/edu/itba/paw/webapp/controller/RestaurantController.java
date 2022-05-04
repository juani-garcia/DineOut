package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.persistence.MenuItem;
import ar.edu.itba.paw.persistence.MenuSection;
import ar.edu.itba.paw.persistence.Restaurant;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.MenuItemForm;
import ar.edu.itba.paw.webapp.form.MenuSectionForm;
import ar.edu.itba.paw.webapp.form.ReservationForm;
import ar.edu.itba.paw.webapp.form.RestaurantForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private ImageService imageService;

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping("")
    public ModelAndView restaurantProfile(Principal principal) {
        final ModelAndView mav = new ModelAndView("restaurant/profile");

        Optional<User> optionalUser = userService.getByUsername(principal.getName());
        if (!optionalUser.isPresent()) throw new IllegalStateException("Not logged in.");
        User user = optionalUser.get();
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElse(null);
        if (restaurant == null) return new ModelAndView("redirect:/restaurant/register");
        mav.addObject("restaurant", restaurant);


        List<MenuSection> menuSectionList = menuSectionService.getByRestaurantId(restaurant.getId());
        menuSectionList.forEach((section) -> section.setMenuItemList(menuItemService.getBySectionId(section.getId())));
        mav.addObject("sections", menuSectionList);
        return mav;
    }

    @RequestMapping("/register")
    public ModelAndView restaurantForm(@ModelAttribute("restaurantForm") final RestaurantForm form) {
        ModelAndView mav = new ModelAndView("register/register_restaurant");
        mav.addObject("categories", Category.values());
        mav.addObject("zones", Zone.values());
        mav.addObject("shifts", Shift.values());
        return mav;
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public ModelAndView create(@Valid @ModelAttribute("restaurantForm") final RestaurantForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return restaurantForm(form);
        }

        User user = securityService.getCurrentUser();
        if (user == null) throw new IllegalStateException("Not logged in");

        restaurantService.create(user.getId(), form.getName(), form.getAddress(), form.getEmail(), form.getDetail(), Zone.getByName(form.getZone()), form.getCategories(), form.getShifts());

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
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElseThrow(() -> new RuntimeException("No hay restaurante"));
        menuSectionService.create(restaurant.getId(), form.getName());
        return new ModelAndView("redirect:/restaurant");
    }

    @RequestMapping(value = "/section/{sectionId}/up", method = {RequestMethod.POST})
    public ModelAndView sectionUp(@PathVariable final long sectionId) {
        menuSectionService.moveUp(sectionId);
        return new ModelAndView("redirect:/restaurant");
    }

    @RequestMapping(value = "/section/{sectionId}/down", method = {RequestMethod.POST})
    public ModelAndView sectionDown(@PathVariable final long sectionId) {
        menuSectionService.moveDown(sectionId);
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

    @RequestMapping(value = "/item", method = {RequestMethod.POST}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} )
    public ModelAndView item(Principal principal, @Valid @ModelAttribute("itemForm") final MenuItemForm form, final BindingResult errors) {
        byte[] imageBytes = null;
        try {
            imageBytes = form.getImage().getBytes();
        } catch (IOException e) {
            errors.addError(new FieldError("itemForm", "image", "Couldn't get image"));
        }

        if (errors.hasErrors()) {
            return itemForm(principal, form);
        }

        User user = userService.getByUsername(principal.getName()).get();
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElseThrow(() -> new RuntimeException("No hay restaurante"));
        MenuItem menuItem = menuItemService.create(form.getName(), form.getDetail(), form.getPrice(), form.getMenuSectionId(), imageBytes);
        return new ModelAndView("redirect:/restaurant");
    }

    @RequestMapping(value = "/item/{itemId}/up", method = {RequestMethod.POST})
    public ModelAndView itemUp(@PathVariable final long itemId) {
        menuItemService.moveUp(itemId);
        return new ModelAndView("redirect:/restaurant");
    }

    @RequestMapping(value = "/item/{itemId}/down", method = {RequestMethod.POST})
    public ModelAndView itemDown(@PathVariable final long itemId) {
        menuItemService.moveDown(itemId);
        return new ModelAndView("redirect:/restaurant");
    }

    @RequestMapping("/view/{resId}")
    public ModelAndView reservation(@PathVariable final long resId, @ModelAttribute("reservationForm") final ReservationForm form) {
        final ModelAndView mav = new ModelAndView("restaurant/public_detail");

        Restaurant restaurant = restaurantService.getById(resId).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);
        mav.addObject("formSuccess", false);
        mav.addObject("shifts", shiftService.getByRestaurantId(resId));
        List<MenuSection> menuSectionList = menuSectionService.getByRestaurantId(restaurant.getId());
        menuSectionList.forEach((section) -> section.setMenuItemList(menuItemService.getBySectionId(section.getId())));
        mav.addObject("sections", menuSectionList);
        List<Shift> shifts = shiftService.getByRestaurantId(restaurant.getId());
        mav.addObject("shifts", shifts);
        return mav;
    }

}

