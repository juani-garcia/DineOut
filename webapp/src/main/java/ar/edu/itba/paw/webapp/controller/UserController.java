package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.SecurityService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.NewPasswordForm;
import ar.edu.itba.paw.webapp.form.PasswordRecoveryForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    private RestaurantService restaurantService;


    @RequestMapping(value = "/restaurant_picker")
    public ModelAndView restaurantPicker(HttpServletRequest request) {
        Restaurant restaurant = restaurantService.getRecommendedRestaurant(request.isUserInRole("DINER"));
        return new ModelAndView("redirect:/restaurant/" + restaurant.getId() + "/view");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerForm(@ModelAttribute("registerForm") final UserForm form) {
        return new ModelAndView("register/register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors, HttpServletRequest request) {

        if (errors.hasErrors()) {
            return registerForm(form);
        }

        userService.create(form.getUsername(), form.getPassword(), form.getFirstName(), form.getLastName(), form.getIsRestaurant(), request.getRequestURL().toString().replace(request.getServletPath(), ""));
        authenticateUserAndSetSession(request, form.getUsername(), form.getPassword());

        return new ModelAndView("redirect:/profile");
    }

    /* Referenced from : https://www.baeldung.com/spring-security-auto-login-user-after-registration#authManager */
    private void authenticateUserAndSetSession(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("home/login");
    }

    @RequestMapping("/profile")
    public ModelAndView profile(HttpServletRequest request) {
        if (request.isUserInRole("RESTAURANT")) {
            return new ModelAndView("redirect:/restaurant");
        } else if (request.isUserInRole("DINER")) {
            return new ModelAndView("redirect:/diner/profile");
        }
        throw new BadCredentialsException("Logged user is neither a RESTAURANT or a DINER");
    }


    // Referenced from: https://www.baeldung.com/spring-security-registration-i-forgot-my-password
    @RequestMapping("/forgot_my_password")
    public ModelAndView forgotMyPassword(@ModelAttribute("passwordRecoveryForm") final PasswordRecoveryForm passwordRecoveryForm) {
        return new ModelAndView("/forgot_my_password");
    }

    @RequestMapping(value = "/reset_password", method = {RequestMethod.POST})
    public ModelAndView resetPassword(@Valid @ModelAttribute("passwordRecoveryForm") final PasswordRecoveryForm passwordRecoveryForm, final BindingResult errors, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return forgotMyPassword(passwordRecoveryForm);
        }

        User user = userService.getByUsername(passwordRecoveryForm.getUsername()).orElseThrow(NotFoundException::new);
        userService.createPasswordResetTokenForUser(user, request.getRequestURL().toString().replace(request.getServletPath(), ""));
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/change_password")
    public ModelAndView changePassword(@RequestParam(name = "token") final String token, @ModelAttribute("newPasswordForm") final NewPasswordForm newPasswordForm) {
        String result = securityService.validatePasswordResetToken(token);
        if (result == null) {
            ModelAndView mav = new ModelAndView("/update_password");
            mav.addObject("token", token);
            return mav;
        }

        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/save_password", method = RequestMethod.POST)
    public ModelAndView savePassword(@Valid @ModelAttribute("newPasswordForm") final NewPasswordForm newPasswordForm, final BindingResult errors) {
        if (errors.hasErrors()) {
            return changePassword(newPasswordForm.getToken(), newPasswordForm);
        }
        String result = securityService.validatePasswordResetToken(newPasswordForm.getToken());

        if (result != null) {
            return new ModelAndView("redirect:/login");
        }

        userService.changePasswordByUserToken(newPasswordForm.getToken(), newPasswordForm.getPassword());
        return new ModelAndView("redirect:/login");
    }
}
