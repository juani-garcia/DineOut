package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.MenuSectionNotFoundException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.InvalidParameterException;
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

    @Autowired
    private ImageService imageService;

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private ReservationService reservationService;

    @RequestMapping("")
    public ModelAndView restaurantProfile() {
        final ModelAndView mav = new ModelAndView("restaurant/profile");

        User user = securityService.getCurrentUser().get();
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElse(null);
        if (restaurant == null) return new ModelAndView("redirect:/restaurant/register");
        mav.addObject("restaurant", restaurant);


        List<MenuSection> menuSectionList = menuSectionService.getByRestaurantId(restaurant.getId());
        menuSectionList.forEach((section) -> section.setMenuItemList(menuItemService.getBySectionId(section.getId())));  // TODO: this should not be here, it could either be on the service or on a join in the dao.
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

        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);

        restaurantService.create(user.getId(), form.getName(), form.getAddress(), form.getEmail(), form.getDetail(), Zone.getByName(form.getZone()), form.getCategories(), form.getShifts());

        return new ModelAndView("redirect:/restaurant");
    }

    @RequestMapping(value = "/section")
    public ModelAndView sectionForm(@ModelAttribute("sectionForm") final MenuSectionForm form) {
        return new ModelAndView("restaurant/section_form");
    }

    @RequestMapping(value = "/section", method = {RequestMethod.POST})
    public ModelAndView section(@Valid @ModelAttribute("sectionForm") final MenuSectionForm form,
                                final BindingResult errors) {
        if (errors.hasErrors()) {
            return sectionForm(form);
        }

        User user = securityService.getCurrentUser().get();
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElseThrow(NotFoundException::new);
        menuSectionService.create(restaurant.getId(), form.getName());
        return new ModelAndView("redirect:/restaurant");
    }

    @RequestMapping(value = "/section/{sectionId}/edit")
    public ModelAndView sectionEditForm(@PathVariable final long sectionId,
                                        @ModelAttribute("sectionForm") final MenuSectionForm form) {
        MenuSection menuSection = menuSectionService.getById(sectionId).orElseThrow(MenuSectionNotFoundException::new);
        ModelAndView mav = new ModelAndView("restaurant/section_edit_form");
        mav.addObject("section", menuSection);
        form.setName(menuSection.getName());
        return mav;
    }

    @RequestMapping(value = "/section/{sectionId}/edit", method = {RequestMethod.POST})
    public ModelAndView sectionEdit(@PathVariable final long sectionId,
                                    @ModelAttribute("sectionForm") final MenuSectionForm form,
                                    final BindingResult errors) {
        if (errors.hasErrors()) {
            return sectionEditForm(sectionId, form);
        }
        menuSectionService.updateName(sectionId, form.getName());
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

    @RequestMapping(value = "/section/{sectionId}/delete", method = {RequestMethod.POST})
    public ModelAndView sectionDeletion(@PathVariable final long sectionId) {
        menuSectionService.delete(sectionId);
        return new ModelAndView("redirect:/restaurant");
    }

    @RequestMapping(value = "/item")
    public ModelAndView itemForm(@ModelAttribute("itemForm") final MenuItemForm form) {
        ModelAndView mav = new ModelAndView("restaurant/item_form");
        User user = securityService.getCurrentUser().get();
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElseThrow(NotFoundException::new);
        List<MenuSection> menuSectionList = menuSectionService.getByRestaurantId(restaurant.getId());
        mav.addObject("sections", menuSectionList);
        return mav;
    }

    @RequestMapping(value = "/item", method = {RequestMethod.POST}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} )
    public ModelAndView item(@Valid @ModelAttribute("itemForm") final MenuItemForm form, final BindingResult errors) {
        byte[] imageBytes = null;
        try {
            imageBytes = form.getImage().getBytes();
        } catch (IOException e) {
            errors.addError(new FieldError("itemForm", "image", "Couldn't get image"));  // TODO: i18n & move elsewere.
        }

        if (errors.hasErrors()) {
            return itemForm(form);
        }

        User user = securityService.getCurrentUser().get();
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElseThrow(() -> new RuntimeException("No hay restaurante"));  // TODO: why do we need to acces the restaurant? @mateo
        MenuItem menuItem = menuItemService.create(form.getName(), form.getDetail(), form.getPrice(), form.getMenuSectionId(), imageBytes);
        return new ModelAndView("redirect:/restaurant");
    }

    @RequestMapping(value = "/item/{itemId}/edit")
    public ModelAndView itemEditForm(@PathVariable final long itemId,
                                     @ModelAttribute("itemForm") final MenuItemForm form) {
        ModelAndView mav = new ModelAndView("restaurant/item_edit_form");
        MenuItem menuItem = menuItemService.getById(itemId).orElseThrow(InvalidParameterException::new);
        mav.addObject("item", menuItem);
        User user = securityService.getCurrentUser().get();
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).get();
        List<MenuSection> menuSectionList = menuSectionService.getByRestaurantId(restaurant.getId());
        mav.addObject("sections", menuSectionList);
        form.setName(menuItem.getName());
        form.setDetail(menuItem.getDetail());
        form.setPrice(menuItem.getPrice());
        form.setMenuSectionId(menuItem.getSectionId());
        // form.setImage(new CommonsMultipartFile(image.getSource()));
        return mav;
    }

    @RequestMapping(value = "/item/{itemId}/edit", method = {RequestMethod.POST})
    public ModelAndView itemEdit(@PathVariable final long itemId,
                                    @ModelAttribute("itemForm") final MenuItemForm form,
                                    final BindingResult errors) {
        if (errors.hasErrors()) {
            return itemEditForm(itemId, form);
        }
        // TODO: manage image update
        menuItemService.edit(itemId, form.getName(), form.getDetail(), form.getPrice(), form.getMenuSectionId(), null);
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

    @RequestMapping(value = "/item/{itemId}/delete", method = {RequestMethod.POST})
    public ModelAndView itemDeletion(@PathVariable final long itemId) {
        menuItemService.delete(itemId);
        return new ModelAndView("redirect:/restaurant");
    }

    @RequestMapping("/view/{resId}")
    public ModelAndView reservation(@PathVariable final long resId, @ModelAttribute("reservationForm") final ReservationForm form) {
        final ModelAndView mav = new ModelAndView("restaurant/public_detail");

        Restaurant restaurant = restaurantService.getById(resId).orElseThrow(NotFoundException::new);
        mav.addObject("restaurant", restaurant);
        mav.addObject("isUserFavorite", favoriteService.isFavoriteOfLoggedUser(resId));
        mav.addObject("formSuccess", false);
        mav.addObject("shifts", shiftService.getByRestaurantId(resId));
        List<MenuSection> menuSectionList = menuSectionService.getByRestaurantId(restaurant.getId());
        menuSectionList.forEach((section) -> section.setMenuItemList(menuItemService.getBySectionId(section.getId())));  // TODO: same as bvefore this should not be here.
        mav.addObject("sections", menuSectionList);
        List<Shift> shifts = shiftService.getByRestaurantId(restaurant.getId());
        mav.addObject("shifts", shifts);
        return mav;
    }

    @RequestMapping("/reservations")
    public ModelAndView reservations(
            @RequestParam(name = "page", defaultValue = "1") final int page,
            @RequestParam(name = "past", defaultValue = "false") final boolean past) {
        ModelAndView mav = new ModelAndView("restaurant/reservations");
        mav.addObject("past", past);
        mav.addObject("pages", reservationService.getPagesCountForCurrentRestaurant(past));
        mav.addObject("reservations", reservationService.getAllForCurrentRestaurant(page, past));
        return mav;
    }

}

