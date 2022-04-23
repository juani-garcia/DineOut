package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.ReservationService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.ReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ReservationController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ReservationService reservationService;

    @RequestMapping("/reserve/{resId}")
    public ModelAndView reservation(@PathVariable final long resId, @ModelAttribute("reservationForm") final ReservationForm form) {
         final ModelAndView mav = new ModelAndView("reservation/reservation");

         mav.addObject("restaurant", restaurantService.getById(resId).orElseThrow(RestaurantNotFoundException::new));
         mav.addObject("formSuccess", false);
         return mav;
    }

    @RequestMapping(value = "/create/{resId}", method = { RequestMethod.POST })
    public ModelAndView create(@PathVariable final long resId, @Valid @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors) {

        if (errors.hasErrors()) {
            return reservation(resId, form);
        }

        reservationService.create(resId, form.getMail(), form.getAmount(), form.getLocalDateTime(), form.getComments());
        final ModelAndView mav =  new ModelAndView("redirect:/reserve/" + resId);
        mav.addObject("restaurant", restaurantService.getById(resId).orElseThrow(RestaurantNotFoundException::new));
        mav.addObject("formSuccess", true);
        return mav;
    }

}
