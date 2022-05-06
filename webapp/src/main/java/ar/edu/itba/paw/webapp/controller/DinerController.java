package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.persistence.Reservation;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/diner")
public class DinerController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private FavoriteService favoriteService;

    @RequestMapping("/profile")
    public ModelAndView profile() {
        if(securityService.getCurrentUsername() == null) throw new IllegalStateException("Invalid user");

        return new ModelAndView("diner/profile");
    }

    @RequestMapping("/reservations")
    public ModelAndView reservations() {
        String username = securityService.getCurrentUsername();
        if(username == null) throw new IllegalStateException("Invalid user");

        List<Reservation> reservationList = reservationService.getAllFutureByUsername(username);
        ModelAndView mav = new ModelAndView("diner/reservations");
        mav.addObject("reservations", reservationList);
        return mav;
    }

    @RequestMapping(value = "/set_favorite/{resId}/{set}", method = {RequestMethod.POST})
    public ModelAndView setFavorite(@PathVariable final long resId, @PathVariable final boolean set) {
        if (set) {
            favoriteService.create(resId, securityService.getCurrentUser().orElseThrow(IllegalArgumentException::new).getId());
        } else {
            favoriteService.delete(resId, securityService.getCurrentUser().orElseThrow(IllegalArgumentException::new).getId());
        }
        return new ModelAndView("redirect:/restaurant/view/" + resId);
    }
}
