package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Zone;
import ar.edu.itba.paw.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("restaurants")
@Component
public class RestaurantController {

    @Autowired
    private final RestaurantService rs;

    @Autowired
    public RestaurantController(final RestaurantService rs) {
        this.rs = rs;
    }

//    // GET /restaurants
//    @GET
//    @Produces(value = {MediaType.APPLICATION_JSON, })
//    public Response listRestaurants(@QueryParam("page") @DefaultValue("1") final int page) {
//        final List<Restaurant> restaurantList = new ArrayList<>(rs.getById(page).orElse(null));
//
//        if (restaurantList.isEmpty()) {
//            return Response.noContent().build();
//        }
//        return Response.ok(new GenericEntity<List<Restaurant>>(restaurantList){})
//                // TODO: complete these
//                .link("", "prev")
//                .link("", "next")
//                .link("", "first")
//                .link("", "last").build();
//    }

    @POST
    public Response createRestaurant(@QueryParam("name") final String name,
                                     // @QueryParam("image") final byte[] image,
                                     @QueryParam("address") final String address,
                                     @QueryParam("mail") final String mail,
                                     @QueryParam("detail") final String detail,
                                     @QueryParam("zone") final Zone zone,
                                     @QueryParam("lat") final Float lat,
                                     @QueryParam("lng") final Float lng,
                                     @QueryParam("categories") final List<Long> categories,
                                     @QueryParam("shifts") final List<Long> shifts) {
        Restaurant newRestaurant = rs.create(name, null, address, mail, detail, zone, lat, lng, categories, shifts);
        return Response.ok(newRestaurant).build();
    }
}
