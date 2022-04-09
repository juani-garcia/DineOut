package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.ReservationService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.form.ReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView webapp(@RequestParam(name = "resId", defaultValue = "1") final long id) {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("restaurant", restaurantService.getRestaurantById(id).orElseThrow(RuntimeException::new));
        return mav;
    }

    @RequestMapping("/reserve")
    public ModelAndView reservation(@ModelAttribute("reservationForm") final ReservationForm form) {
            return new ModelAndView("reservation");
    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors) {
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
            return reservation(form);
        }

        reservationService.createReservation(1, form.getMail(), form.getAmount(), LocalDateTime.of(localDate, localTime), form.getComments());
        return new ModelAndView("redirect:/");
    }

}
