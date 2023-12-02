package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserRole;

import javax.persistence.*;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class UserDTO {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private Locale locale;
    private Set<UserRole> roles;
    private URI self;
    private URI favoriteRestaurants;
    private URI reviews;
    private URI reservations;

    public static UserDTO fromUser(final UriInfo uriInfo, final User user) {
        final UserDTO dto = new UserDTO();
        final UriBuilder userUriBuilder = UserDTO.getUriBuilder(uriInfo, user);

        dto.id = user.getId();
        dto.username = user.getUsername();
        dto.firstName = user.getFirstName();
        dto.lastName = user.getLastName();
        dto.locale = user.getLocale();
        dto.roles = new HashSet<>(user.getRoles());
        dto.self = userUriBuilder.build();
        dto.favoriteRestaurants = RestaurantDTO.getUriBuilderForUserFavorites(uriInfo, user).build();
        dto.reviews = RestaurantReviewDTO.getUriBuilderByUser(uriInfo, user).build();
        dto.reservations = ReservationDTO.getUriBuilderByUser(uriInfo, user).build();

        return dto;
    }

    public static UriBuilder getUriBuilder(final UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("users");
    }

    public static UriBuilder getUriBuilder(final UriInfo uriInfo, final User user) {
        return UserDTO.getUriBuilder(uriInfo).path(String.valueOf(user.getId()));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getFavoriteRestaurants() {
        return favoriteRestaurants;
    }

    public void setFavoriteRestaurants(URI favoriteRestaurants) {
        this.favoriteRestaurants = favoriteRestaurants;
    }

    public URI getReviews() {
        return reviews;
    }

    public void setReviews(URI reviews) {
        this.reviews = reviews;
    }

    public URI getReservations() {
        return reservations;
    }

    public void setReservations(URI reservations) {
        this.reservations = reservations;
    }

}
