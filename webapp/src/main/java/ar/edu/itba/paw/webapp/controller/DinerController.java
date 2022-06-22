package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Favorite;
import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.ForbiddenActionException;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.service.FavoriteService;
import ar.edu.itba.paw.service.ReservationService;
import ar.edu.itba.paw.service.SecurityService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.UserProfileEditForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/diner")
public class DinerController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserService userService;

    @RequestMapping("/profile")
    public ModelAndView profile() {
        ModelAndView mav = new ModelAndView("diner/profile");
        mav.addObject("user", securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new));
        return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@ModelAttribute("userProfileEditForm") final UserProfileEditForm userProfileEditForm) {
        ModelAndView mav = new ModelAndView("diner/edit");
        User user = securityService.getCurrentUser().orElseThrow(ForbiddenActionException::new);
        userProfileEditForm.setFirstName(user.getFirstName());
        userProfileEditForm.setLastName(user.getLastName());
        return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editProfile(@Valid @ModelAttribute("userProfileEditForm") final UserProfileEditForm userProfileEditForm, final BindingResult errors, final HttpServletRequest request) {

        if (errors.hasErrors()) {
            return edit(userProfileEditForm);
        }

        userService.edit(securityService.getCurrentUser().orElseThrow(ForbiddenActionException::new), userProfileEditForm.getFirstName(), userProfileEditForm.getLastName(), request.getRequestURL().toString().replace(request.getServletPath(), ""));

        return new ModelAndView("redirect:/diner/profile");
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
        PagedQuery<Favorite> favoritePagedQuery = favoriteService.getAllOfLoggedUser(page);
        long pages = favoritePagedQuery.getPageCount();
        if (page != 1 && pages < page) return new ModelAndView("redirect:/diner/favorites" + "?page=" + pages);
        // This is because if a user has X favorites in page N and he un favorites them all, and goes back with <-
        // a "no favorites" sign would be shown, although he has favorites in pages N-i (1 <= i < N).
        ModelAndView mav = new ModelAndView("diner/favorites");
        mav.addObject("favorites", favoritePagedQuery.getContent());
        mav.addObject("pages", pages);
        return mav;
    }

    @RequestMapping(value = "/favorite/{resId}/{set}", method = {RequestMethod.POST})
    public ModelAndView setFavorite(@PathVariable final long resId, @PathVariable final boolean set) {
        favoriteService.set(resId, securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new).getId(), set);
        return new ModelAndView("redirect:/restaurant/" + resId + "/view");
    }
}
