package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.persistence.Reservation;
import ar.edu.itba.paw.persistence.Restaurant;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
        if (securityService.getCurrentUsername() == null) throw new IllegalStateException("Invalid user");

        return new ModelAndView("diner/profile");
    }

    @RequestMapping("/reservations")
    public ModelAndView reservations(
            @RequestParam(name = "page", defaultValue = "1") final int page,
            @RequestParam(name = "past", defaultValue = "false") final boolean past) {
        String username = securityService.getCurrentUsername();
        if (username == null) throw new IllegalStateException("Invalid user");

        List<Reservation> reservationList = reservationService.getAllByUsername(username, page, past);
        ModelAndView mav = new ModelAndView("diner/reservations");
        mav.addObject("reservations", reservationList);
        mav.addObject("past", past);
        return mav;
    }

    @RequestMapping("/favorites")
    public ModelAndView favorites(@RequestParam(name = "page", defaultValue = "1") final int page) {
        List<Restaurant> restaurantList = favoriteService.getRestaurantList(page);
        ModelAndView mav = new ModelAndView("diner/favorites");
        mav.addObject("restaurants", restaurantList);
        mav.addObject("pageSize", 3); // TODO: remove magin number for PAGE_SIZE getter from dao.
        mav.addObject("totalRestaurantCount", favoriteService.getFavoriteCount());
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
