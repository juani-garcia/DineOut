package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Zone;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.dto.RestaurantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("restaurants")
@Component
public class RestaurantController {

    @Autowired
    private final RestaurantService rs;

    @Context
    private UriInfo uriInfo;

    @Autowired
    public RestaurantController(final RestaurantService rs) {
        this.rs = rs;
    }

// TODO: Clean up example

//    // GET /restaurants
//    @GET
//    @Produces(value = {MediaType.APPLICATION_JSON, })
//    public Response listRestaurants(@QueryParam("page") @DefaultValue("1") final int page) {
//        final List<RestaurantDTO> restaurantList = rs.getAll(page).stream().map(RestaurantDTO::fromRestaurant).collect(Collectors.toList());
//
//        if (restaurantList.isEmpty()) {
//            return Response.noContent().build();
//        }
//        return Response.ok(new GenericEntity<List<RestaurantDTO>>(restaurantList){})
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
        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newRestaurant.getId())).build();
        return Response.created(location).build();
    }

    // GET /restaurants/{id}
    @GET
    @Path("/{id}")
    public Response getRestaurant(@PathParam("id") final long restaurantID) {
        Optional<RestaurantDTO> maybeRestaurant = rs.getById(restaurantID).map(r -> RestaurantDTO.fromRestaurant(uriInfo, r));
        if (! maybeRestaurant.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
         return Response.ok(maybeRestaurant.get()).build();
    }

//    @DELETE
//    @Path("/{id}")
//    @Produces(value = {MediaType.APPLICATION_JSON, })
//    public Response deleteById(@PathParam("id") final long id) {
//        rs.deleteById(id);
//        return Response.noContent().build();
//    }

}
