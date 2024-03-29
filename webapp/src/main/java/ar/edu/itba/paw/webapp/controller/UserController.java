package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.FavoriteNotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.FavoriteService;
import ar.edu.itba.paw.service.SecurityService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import ar.edu.itba.paw.webapp.form.*;
import ar.edu.itba.paw.webapp.utils.PATCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

@Path("users")
@Component
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;
    private final FavoriteService favoriteService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private Environment env;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, SecurityService securityService, FavoriteService favoriteService) {
        this.userService = userService;
        this.securityService = securityService;
        this.favoriteService = favoriteService;
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
                getBaseURL());
        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newUser.getId())).build();
        return Response.created(location).build();
    }

    @GET
    @Path("/{id}")
    @PreAuthorize("@securityManager.isUserOfId(authentication, #userId)")
    public Response readUser(@PathParam("id") final long userId) {
        Optional<UserDTO> maybeUser = userService.getById(userId).map(u -> UserDTO.fromUser(uriInfo, u));
        if (! maybeUser.isPresent()) {
            throw new UserNotFoundException();
        }
        return Response.ok(maybeUser.get()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("@securityManager.isUserOfId(authentication, #userId)")
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
                getBaseURL()
        );
        return Response.ok().build();
    }

    @POST
    @Path("/password-recovery-token")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createPasswordRecoveryToken(@Valid PasswordRecoveryForm passwordRecoveryForm) {
        userService.createPasswordResetTokenByUsername(passwordRecoveryForm.getUsername(), getBaseURL());
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("@securityManager.isUserOfId(authentication, #userId)")
    public Response editPasswordByToken(@PathParam("id") Long userId,
            @Valid NewPasswordForm newPasswordForm) {
        final User user = securityService.checkCurrentUser(userId);
        userService.editPassword(user, newPasswordForm.getPassword());
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/favorites/{restaurantId}")
    public Response readFavoriteRelation(
            @PathParam("id") final long userId,
            @PathParam("restaurantId") final long restaurantId
    ) {
        final boolean favorite = favoriteService.isFavoriteOfUser(userId, restaurantId);
        if (!favorite) {
            throw new FavoriteNotFoundException();
        }
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}/favorites/{restaurantId}")
    @PreAuthorize("@securityManager.isUserOfId(authentication, #userId)")
    public Response updateFavoriteRelation(
            @PathParam("id") final long userId,
            @PathParam("restaurantId") final long restaurantId,
            @Valid FavoriteForm fm
            ) {
        favoriteService.set(restaurantId, userId, fm.isUpVote());
        return Response.ok().build();
    }

    private String getBaseURL() {
        return env.getProperty("url.base");
    }

}
