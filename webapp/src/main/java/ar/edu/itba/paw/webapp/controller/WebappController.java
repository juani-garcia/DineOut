package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.ReservationService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.form.ReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
public class WebappController {

    private final RestaurantService restaurantService;
    private final ReservationService reservationService;

     @Autowired
     public WebappController(final RestaurantService restaurantService, ReservationService reservationService) {
         this.restaurantService = restaurantService;
         this.reservationService = reservationService;
     }

    @RequestMapping(value = "/")
    public ModelAndView webapp(@RequestParam(name = "page", defaultValue = "1") final int page) {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("restaurants", restaurantService.getAll(page));
        return mav;
    }

    @RequestMapping("/reserve/{resId}")
    public ModelAndView reservation(@PathVariable final long resId, @ModelAttribute("reservationForm") final ReservationForm form) {
         final ModelAndView mav = new ModelAndView("reservation");
         mav.addObject("resId", resId);
         return mav;
    }

    @RequestMapping(value = "/create/{resId}", method = { RequestMethod.POST })
    public ModelAndView create(@PathVariable final long resId, @Valid @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate localDate = null;
        LocalTime localTime = null;

        try {
            localDate = LocalDate.parse(form.getDate(), dateFormatter);
        } catch (DateTimeParseException e) {
            errors.addError(new FieldError("date", "date", e.getMessage()));
        }
        try {
            localTime = LocalTime.parse(form.getTime(), timeFormatter);
        } catch (DateTimeParseException e) {
            errors.addError(new FieldError("time", "time", e.getMessage()));
        }

        if (errors.hasErrors()) {
            return reservation(resId, form);
        }

        reservationService.createReservation(resId, form.getMail(), form.getAmount(), LocalDateTime.of(localDate, localTime), form.getComments());
        return new ModelAndView("redirect:/");
    }

}
