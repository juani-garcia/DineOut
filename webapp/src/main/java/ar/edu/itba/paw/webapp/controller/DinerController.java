package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.User;
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
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private SecurityController securityController;

    @RequestMapping("/profile")
    public ModelAndView profile() {
        Optional<User> loggedInUser = userService.getByUsername(securityController.getCurrentUserName());
        if (!loggedInUser.isPresent()) throw new IllegalStateException("Current user is not valid");
        ModelAndView mav = new ModelAndView("diner/profile");
        mav.addObject("user", loggedInUser.get());
        return mav;
    }

    @RequestMapping("/reservations")
    public ModelAndView reservations() {
        String username = securityController.getCurrentUserName();
        List<Reservation> reservationList = reservationService.getAllByUsername(username);
        ModelAndView mav = new ModelAndView("diner/reservations");
        mav.addObject("reservations", reservationList);
        return mav;
    }
}
