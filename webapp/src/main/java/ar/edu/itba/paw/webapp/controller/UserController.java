package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.service.SecurityService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import ar.edu.itba.paw.webapp.form.NewPasswordForm;
import ar.edu.itba.paw.webapp.form.PasswordRecoveryForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import ar.edu.itba.paw.webapp.form.UserProfileEditForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
    private final UserService userService;
    private final SecurityService securityService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GET
    @Path("/{id}")
    @PreAuthorize("@securityManager.isUserOfId(authentication, #userID)")
    public Response readUser(@PathParam("id") final long userID) {
        Optional<UserDTO> maybeUser = userService.getById(userID).map(u -> UserDTO.fromUser(uriInfo, u));
        if (! maybeUser.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(maybeUser.get()).build();
    }

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON, })
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
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("@securityManager.isUserOfId(authentication, #userID)")
    public Response updateUser(
            @PathParam("id") final long userId,
            @Valid final UserProfileEditForm userProfileEditForm
    ) {
        Optional<User> user = securityService.getCurrentUser();
        if (! user.isPresent()) {
            throw new UnauthenticatedUserException(); // Should not happen due to @PreAuthorize
        }
        userService.edit(
                user.get(),
                userProfileEditForm.getFirstName(),
                userProfileEditForm.getLastName(),
                uriInfo.getPath()
        );
        return Response.ok().build();
    }

    @POST
    @Path("/password-recovery-token")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createPasswordRecoveryToken(@Valid PasswordRecoveryForm passwordRecoveryForm) {
        userService.createPasswordResetTokenByUsername(passwordRecoveryForm.getUsername(), uriInfo.getPath());
        return Response.ok().build();
    }

    @PUT
    @Path("/password-recovery-token/{token}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response editPasswordByToken(@Valid NewPasswordForm newPasswordForm,
                                        @PathParam("token") String token) {
        userService.changePasswordByUserToken(newPasswordForm.getToken(), newPasswordForm.getPassword());
        return Response.ok().build();
    }

}
