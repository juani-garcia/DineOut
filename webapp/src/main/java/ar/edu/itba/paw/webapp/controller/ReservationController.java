package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.persistence.Restaurant;
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

    @Autowired
    private ShiftService shiftService;

    @RequestMapping("/reserve/{resId}")
    public ModelAndView reservation(
            @PathVariable final long resId,
            @ModelAttribute("reservationForm") final ReservationForm form) {
        final ModelAndView mav = new ModelAndView("reservation/reservation");
         Restaurant restaurant = restaurantService.getById(resId).orElseThrow(NotFoundException::new);
         mav.addObject("restaurant", restaurant);
         mav.addObject("times", Shift.availableTimes(shiftService.getByRestaurantId(resId), 30));
         return mav;
    }

    @RequestMapping(value = "/create/{resId}", method = {RequestMethod.POST})
    public ModelAndView create(@PathVariable final long resId, @Valid @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors) {

        if (errors.hasErrors()) {
            return reservation(resId, form);
        }

        reservationService.create(resId, securityService.getCurrentUsername(), form.getAmount(), form.getLocalDateTime(), form.getComments());

        return new ModelAndView("redirect:/diner/reservations");
    }

    @RequestMapping(value = "/reservation/{resId}/delete", method = {RequestMethod.POST})
    public ModelAndView delete(@PathVariable final long resId, HttpServletRequest request) {
        reservationService.delete(resId);
        if (request.isUserInRole("DINER")) {
            return new ModelAndView("redirect:/diner/reservations");
        } else {
            return new ModelAndView("redirect:/restaurant/reservations");
        }
    }

    @RequestMapping(value = "/reservation/{resId}/confirm", method = {RequestMethod.POST})
    public ModelAndView confirm(@PathVariable final long resId) {
        reservationService.confirm(resId);
        return new ModelAndView("redirect:/restaurant/reservations");
    }


}
