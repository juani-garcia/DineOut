package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebappController {

    private final RestaurantService rs;

     @Autowired
     public WebappController(final RestaurantService rs) {
         this.rs = rs;
     }

    @RequestMapping(value = "/")
    public ModelAndView webapp(@RequestParam(name = "resId", defaultValue = "1") final long id) {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("restaurant", rs.getRestaurantById(id).orElseThrow(RuntimeException::new));
        return mav;
    }

}
