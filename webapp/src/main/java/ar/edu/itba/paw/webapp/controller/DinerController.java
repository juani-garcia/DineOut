package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.service.FavoriteService;
import ar.edu.itba.paw.service.ReservationService;
import ar.edu.itba.paw.service.SecurityService;
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
        ModelAndView mav = new ModelAndView("diner/profile");
        mav.addObject("user", securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new));
        return mav;
    }

    @RequestMapping("/reservations")
    public ModelAndView reservations(
            @RequestParam(name = "page", defaultValue = "1") final int page,
            @RequestParam(name = "past", defaultValue = "false") final boolean past) {
        PagedQuery<Reservation> reservationList = reservationService.getAllForCurrentUser(page, past);
        long pages = reservationList.getPageCount();
        if (page != 1 && pages < page) return new ModelAndView("redirect:/diner/reservations" + "?page=" + pages);

        ModelAndView mav = new ModelAndView("diner/reservations");
        mav.addObject("reservations", reservationList.getContent());
        mav.addObject("pages", pages);
        mav.addObject("past", past);
        return mav;
    }

    @RequestMapping("/favorites")
    public ModelAndView favorites(@RequestParam(name = "page", defaultValue = "1") final int page) {
        long pages = favoriteService.getFavoritePageCount();
        if (page != 1 && pages < page) return new ModelAndView("redirect:/diner/favorites" + "?page=" + pages);
        // This is because if a user has X favorites in page N and he un favorites them all, and goes back with <-
        // a "no favorites" sign would be shown, although he has favorites in pages N-i (1 <= i < N).
        List<Restaurant> restaurantList = favoriteService.getRestaurantList(page);
        ModelAndView mav = new ModelAndView("diner/favorites");
        mav.addObject("restaurants", restaurantList);
        mav.addObject("pages", pages);
        return mav;
    }

    @RequestMapping(value = "/favorite/{resId}/{set}", method = {RequestMethod.POST})
    public ModelAndView setFavorite(@PathVariable final long resId, @PathVariable final boolean set) {
        favoriteService.set(resId, securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new).getId(), set);
        return new ModelAndView("redirect:/restaurant/" + resId + "/view");
    }
}
