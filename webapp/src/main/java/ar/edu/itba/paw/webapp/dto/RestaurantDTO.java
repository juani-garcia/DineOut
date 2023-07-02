package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.*;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class RestaurantDTO {

    private Image image;
    private Long favCount;
    private Long rating;
    private Long ratingCount;
    private String name;
    private Float lat;
    private Float lng;
    private String mail;
    private String address;
    private String detail;

    private URI self;
    private URI user;
    // TODO: Consider Zone, Shifts, Categories, MenuSection

    public static RestaurantDTO fromRestaurant(final UriInfo uriInfo, final Restaurant restaurant) {
        final RestaurantDTO dto = new RestaurantDTO();

        dto.name = restaurant.getName();
        final UriBuilder restaurantUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("restaurants").path(String.valueOf(restaurant.getId()));
        dto.self = restaurantUriBuilder.build();
        final UriBuilder usersUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("users");
        // TODO: Remove this example
        // dto.user = usersUriBuilder.clone()
        // .queryParam("assignedTo", String.valueOf(restaurant.getId())).build();
        dto.user = usersUriBuilder
                .path(String.valueOf(restaurant.getUser().getId())).build();
        // TODO: Complete

        return dto;
    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Long getFavCount() {
        return favCount;
    }

    public void setFavCount(Long favCount) {
        this.favCount = favCount;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Long ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getUser() {
        return user;
    }

    public void setUser(URI user) {
        this.user = user;
    }
}
