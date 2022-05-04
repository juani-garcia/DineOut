package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.persistence.Reservation;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/diner")
public class DinerController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping("/profile")
    public ModelAndView profile() {
        // TODO: check if necessary
        if(securityService.getCurrentUsername() == null) throw new IllegalStateException("Invalid user");

        return new ModelAndView("diner/profile");
    }

    @RequestMapping("/reservations")
    public ModelAndView reservations() {
        String username = securityService.getCurrentUsername();
        // TODO: check if necessary
        if(username == null) throw new IllegalStateException("Invalid user");

        List<Reservation> reservationList = reservationService.getAllByUsername(username);
        ModelAndView mav = new ModelAndView("diner/reservations");
        mav.addObject("reservations", reservationList);
        return mav;
    }
}
