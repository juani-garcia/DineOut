package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestaurantDTO {
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
    private URI owner;
    private URI image;
    // TODO: Consider id, Zone, Shifts, Categories, MenuSection

    public static RestaurantDTO fromRestaurant(final UriInfo uriInfo, final Restaurant restaurant) {
        final RestaurantDTO dto = new RestaurantDTO();

        dto.favCount = restaurant.getFavCount();
        dto.rating = restaurant.getRating();
        dto.ratingCount = restaurant.getRatingCount();
        dto.name = restaurant.getName();
        dto.lat = restaurant.getLat();
        dto.lng = restaurant.getLng();
        dto.mail = restaurant.getMail();
        dto.address = restaurant.getAddress();
        dto.detail = restaurant.getDetail();

        final UriBuilder restaurantUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("restaurants").path(String.valueOf(restaurant.getId()));
        dto.self = restaurantUriBuilder.build();
        final UriBuilder usersUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("users");
        // TODO: Remove this example
        // dto.user = usersUriBuilder.clone()
        // .queryParam("assignedTo", String.valueOf(restaurant.getId())).build();
        dto.owner = usersUriBuilder
                .path(String.valueOf(restaurant.getUser().getId())).build();
        // TODO: Complete

        return dto;
    }


    public URI getImage() {
        return image;
    }

    public void setImage(URI image) {
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

    public URI getOwner() {
        return owner;
    }

    public void setOwner(URI user) {
        this.owner = user;
    }
}
