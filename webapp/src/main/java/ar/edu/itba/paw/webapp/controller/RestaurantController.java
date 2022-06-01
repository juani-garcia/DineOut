package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.MenuSectionNotFoundException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.MenuItemForm;
import ar.edu.itba.paw.webapp.form.MenuSectionForm;
import ar.edu.itba.paw.webapp.form.RestaurantForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuSectionService menuSectionService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private ReservationService reservationService;

    @RequestMapping("")
    public ModelAndView restaurantProfile() {
        final ModelAndView mav = new ModelAndView("restaurant/profile");

        Restaurant restaurant = restaurantService.getOfLoggedUser().orElse(null);
        if (restaurant == null) return new ModelAndView("redirect:/restaurant/register");
        mav.addObject("restaurant", restaurant);


        List<MenuSection> menuSectionList = menuSectionService.getByRestaurantId(restaurant.getId());
        mav.addObject("sections", menuSectionList);
        mav.addObject("shifts", restaurant.getShifts());
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

    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView create(@Valid @ModelAttribute("restaurantForm") final RestaurantForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return restaurantForm(form);
        }
        byte[] image;
        try {
            image = form.getImage().getBytes();
        } catch (IOException e) {
            throw new  IllegalStateException(); // This should never happen because of @ValidImage.
        }
        restaurantService.create(form.getName(), image, form.getAddress(), form.getEmail(), form.getDetail(), Zone.getByName(form.getZone()), form.getCategories(), form.getShifts());

        return new ModelAndView("redirect:/restaurant");
    }

    @RequestMapping(value = "/edit")
    public ModelAndView restaurantEditForm(@ModelAttribute("restaurantForm") final RestaurantForm form) {
        ModelAndView mav = new ModelAndView("restaurant/edit_restaurant");
        Restaurant restaurant = restaurantService.getByUserID(securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new).getId()).orElseThrow(NotFoundException::new);
        mav.addObject("categories", Category.values());
        mav.addObject("zones", Zone.values());
        mav.addObject("shifts", Shift.values());
        form.setName(restaurant.getName());
        form.setAddress(restaurant.getAddress());
        form.setEmail(restaurant.getMail());
        form.setDetail(restaurant.getDetail());
        form.setZone(restaurant.getZone().getName());
        form.setCategories(restaurant.getCategories().
                stream().mapToLong(Category::getId).boxed().collect(Collectors.toList()));
        form.setShifts(restaurant.getShifts().
                stream().mapToLong(Shift::getId).boxed().collect(Collectors.toList()));

        return mav;
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.POST}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView restaurantEdit(@Valid @ModelAttribute("restaurantForm") final RestaurantForm form,
                                       final BindingResult errors) {
        if (errors.hasErrors()) {
            return restaurantEditForm(form);
        }

        byte[] image;
        try {
            image = form.getImage().getBytes();
        } catch (IOException e) {
            throw new  IllegalStateException(); // This should never happen because of @ValidImage.
        }

        restaurantService.updateCurrentRestaurant(form.getName(), form.getAddress(), form.getEmail(), form.getDetail(), Zone.getByName(form.getZone()), form.getCategories(), form.getShifts(), image);

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

        menuSectionService.create(form.getName());
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
                                    @Valid @ModelAttribute("sectionForm") final MenuSectionForm form,
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
        Restaurant restaurant = restaurantService.getOfLoggedUser().orElseThrow(NotFoundException::new);
        List<MenuSection> menuSectionList = menuSectionService.getByRestaurantId(restaurant.getId());
        mav.addObject("sections", menuSectionList);
        return mav;
    }

    @RequestMapping(value = "/item", method = {RequestMethod.POST}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView item(@Valid @ModelAttribute("itemForm") final MenuItemForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return itemForm(form);
        }

        byte[] image;
        try {
            image = form.getImage().getBytes();
        } catch (IOException e) {
            throw new IllegalStateException(); // This should never happen because of @ValidImage.
        }

        MenuItem menuItem = menuItemService.create(form.getName(), form.getDetail(), form.getPrice(), form.getMenuSectionId(), image);
        return new ModelAndView("redirect:/restaurant");
    }

    @RequestMapping(value = "/item/{itemId}/edit")
    public ModelAndView itemEditForm(@PathVariable final long itemId,
                                     @ModelAttribute("itemForm") final MenuItemForm form) {
        ModelAndView mav = new ModelAndView("restaurant/item_edit_form");
        MenuItem menuItem = menuItemService.getById(itemId).orElseThrow(InvalidParameterException::new);
        mav.addObject("item", menuItem);
        Restaurant restaurant = restaurantService.getOfLoggedUser().orElseThrow(NotFoundException::new);
        List<MenuSection> menuSectionList = menuSectionService.getByRestaurantId(restaurant.getId());
        mav.addObject("sections", menuSectionList);
        form.setName(menuItem.getName());
        form.setDetail(menuItem.getDetail());
        form.setPrice(menuItem.getPrice());
        form.setMenuSectionId(menuItem.getSection().getId());
        return mav;
    }

    @RequestMapping(value = "/item/{itemId}/edit", method = {RequestMethod.POST}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView itemEdit(@PathVariable final long itemId,
                                 @Valid @ModelAttribute("itemForm") final MenuItemForm form,
                                 final BindingResult errors) {
        if (errors.hasErrors()) {
            return itemEditForm(itemId, form);
        }

        byte[] image;
        try {
            image = form.getImage().getBytes();
        } catch (IOException e) {
            throw new  IllegalStateException(); // This should never happen because of @ValidImage.
        }

        menuItemService.edit(itemId, form.getName(), form.getDetail(), form.getPrice(), form.getMenuSectionId(), image);
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
    public ModelAndView reservation(@PathVariable final long resId) {
        final ModelAndView mav = new ModelAndView("restaurant/public_detail");

        Restaurant restaurant = restaurantService.getById(resId).orElseThrow(NotFoundException::new);
        mav.addObject("restaurant", restaurant);
        mav.addObject("isUserFavorite", favoriteService.isFavoriteOfLoggedUser(resId));
        mav.addObject("formSuccess", false);
        mav.addObject("shifts", restaurant.getShifts());
        List<MenuSection> menuSectionList = menuSectionService.getByRestaurantId(restaurant.getId());
        mav.addObject("sections", menuSectionList);
        return mav;
    }

    @RequestMapping("/reservations")
    public ModelAndView reservations(
            @RequestParam(name = "page", defaultValue = "1") final int page,
            @RequestParam(name = "past", defaultValue = "false") final boolean past) {
        PagedQuery<Reservation> reservationPagedQuery =
                reservationService.getAllForCurrentRestaurant(page, past);
        long pages = reservationPagedQuery.getPageCount();
        if (page != 1 && pages < page) return new ModelAndView("redirect:/restaurant/reservations" + "?page=" + pages);

        ModelAndView mav = new ModelAndView("restaurant/reservations");
        mav.addObject("past", past);
        mav.addObject("pages", pages);
        mav.addObject("reservations", reservationPagedQuery.getContent());
        return mav;
    }

}

