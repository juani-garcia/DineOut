package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.MenuSection;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private MenuSectionService menuSectionService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @RequestMapping("/menu/{resID}")
    public ModelAndView menu(@PathVariable final long resID) {
        final ModelAndView mav = new ModelAndView("reservation/menu");
        List<MenuSection> menuSectionList = menuSectionService.getByRestaurantId(resID);
        for (MenuSection section: menuSectionList) {
            section.setMenuItemList(menuItemService.getBySectionId(section.getId()));
        }
        mav.addObject("sections", menuSectionList);
        return mav;
    }

//    @RequestMapping("/edit_restaurant_menu")
//    public ModelAndView edit() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();   // TODO: Improve how we ask for users
//        Optional<User> user = userService.getByUsername(auth.getName());
//        if (!user.isPresent()) throw new IllegalStateException("Tenes que estar loggeado para realizar esta accion.");
//        long userID = user.get().getId();
//    }
}
