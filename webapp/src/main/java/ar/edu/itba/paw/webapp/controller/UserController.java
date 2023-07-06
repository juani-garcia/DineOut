package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.SecurityService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Optional;

@Path("users")
@Component
public class UserController {
    private UserService userService;
    private SecurityService securityService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    public UserController(UserService userService,
                          SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GET
    @Path("/{id}")
    @PreAuthorize("@securityManager.validateAuthById(authentication, #userID)")
    public Response readUser(@PathParam("id") final long userID) {
        Optional<UserDTO> maybeUser = userService.getById(userID).map(u -> UserDTO.fromUser(uriInfo, u));
        if (! maybeUser.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(maybeUser.get()).build();
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

    @PUT
    @Path("/{id}")
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED, })
    public Response editUser(@Valid final UserForm userForm) {
        final Optional<User> current = securityService.getCurrentUser();

        if(!current.isPresent())
            return Response.status(Response.Status.UNAUTHORIZED).build();

        final Optional<User> edited = userService.edit(
                current.get(),
                userForm.getFirstName(),
                userForm.getLastName(),
                uriInfo.getPath());

        if(!edited.isPresent())
            return Response.status(Response.Status.FORBIDDEN).build();

        return Response.noContent().build();
    }

}
