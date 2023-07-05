package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.SecurityService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("users")
@Component
public class UserController {

    private SecurityService securityService;
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    public UserController(SecurityService securityService,
                          UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED, })
    public Response createUser(@Valid final UserForm userForm) {
        final User newUser = userService.create(
                userForm.getUsername(),
                userForm.getPassword(),
                userForm.getFirstName(),
                userForm.getLastName(),
                userForm.getIsRestaurant(),
                uriInfo.getPath());
        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newUser.getId())).build();
        return Response.created(location).build();
    }

    @GET
    @Path("/{id}")
    public Response readUser(@PathParam("id") final long userID) {
        Optional<UserDTO> maybeUser = userService.getById(userID).map(u -> UserDTO.fromUser(uriInfo, u));
        if (! maybeUser.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(maybeUser.get()).build();
    }

}
