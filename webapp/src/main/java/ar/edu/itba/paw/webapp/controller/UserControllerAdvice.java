package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserControllerAdvice {
    @Autowired
    private SecurityService securityService;

    @ModelAttribute
    public void addUser(Model model) {
        model.addAttribute("user", securityService.getCurrentUser().orElse(null));
    }

    @ModelAttribute
    public void addRoles(Model model) {
        model.addAttribute("RESTAURANT", Role.RESTAURANT.getRoleName());
        model.addAttribute("DINER", Role.DINER.getRoleName());
    }
}