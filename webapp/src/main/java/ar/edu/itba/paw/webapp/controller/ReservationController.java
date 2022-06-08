package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.webapp.form.ReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ReservationController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping("/reserve/{resId}")
    public ModelAndView reservation(
            @PathVariable final long resId,
            @ModelAttribute("reservationForm") final ReservationForm form) {
        final ModelAndView mav = new ModelAndView("reservation/reservation");
         Restaurant restaurant = restaurantService.getById(resId).orElseThrow(NotFoundException::new);
         mav.addObject("restaurant", restaurant);
         mav.addObject("times", Shift.availableTimes(restaurant.getShifts(), 30));
         return mav;
    }

    @RequestMapping(value = "/create/{resId}", method = {RequestMethod.POST})
    public ModelAndView create(@PathVariable final long resId, @Valid @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors, HttpServletRequest request) {

        if (errors.hasErrors()) {
            return reservation(resId, form);
        }

        reservationService.create(resId, securityService.getCurrentUsername(), form.getAmount(), form.getLocalDateTime(), form.getComments(), request.getRequestURL().toString().replace(request.getServletPath(), ""));

        return new ModelAndView("redirect:/diner/reservations");
    }

    @RequestMapping(value = "/reservation/{resId}/delete", method = {RequestMethod.POST})
    public ModelAndView delete(@PathVariable final long resId, HttpServletRequest request) {
        reservationService.delete(resId, request.getRequestURL().toString().replace(request.getServletPath(), ""));
        if (request.isUserInRole(Role.DINER.getRoleName())) {
            return new ModelAndView("redirect:/diner/reservations");
        } else {
            return new ModelAndView("redirect:/restaurant/reservations");
        }
    }

    @RequestMapping(value = "/reservation/{resId}/confirm", method = {RequestMethod.POST})
    public ModelAndView confirm(@PathVariable final long resId, HttpServletRequest request) {
        reservationService.confirm(resId, request.getRequestURL().toString().replace(request.getServletPath(), ""));
        return new ModelAndView("redirect:/restaurant/reservations");
    }


}
