package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.persistence.Reservation;
import ar.edu.itba.paw.persistence.Restaurant;
import ar.edu.itba.paw.model.exceptions.InvalidTimeException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.model.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.ReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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

         Restaurant restaurant = restaurantService.getById(resId).orElseThrow(RestaurantNotFoundException::new);
         mav.addObject("restaurant", restaurant);
         return mav;
    }

    @RequestMapping(value = "/create/{resId}", method = { RequestMethod.POST })
    public ModelAndView create(@PathVariable final long resId, @Valid @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors) {

        if (errors.hasErrors()) {
            return reservation(resId, form);
        }

        // TODO: i18n.
        try {
            reservationService.create(resId, securityService.getCurrentUsername(), form.getAmount(), form.getLocalDateTime(), form.getComments());
        } catch (InvalidTimeException e) {
            errors.addError(new FieldError("reservationForm", "dateTime", "El horario de la reserva es inválido."));  // TODO: remove this error from here.
            return reservation(resId, form);
        }

        return new ModelAndView("redirect:/diner/reservations");
    }

    @RequestMapping(value = "/reservation/{resId}/delete", method = {RequestMethod.POST})
    public ModelAndView delete(@PathVariable final long resId, HttpServletRequest request) {
        reservationService.delete(resId);
        if(request.isUserInRole("DINER")) {
            return new ModelAndView("redirect:/diner/reservations");
        } else {
            return new ModelAndView("redirect:/restaurant/reservations");
        }
    }


}
