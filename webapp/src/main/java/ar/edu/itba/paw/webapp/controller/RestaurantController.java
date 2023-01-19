package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Zone;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.dto.RestaurantDTO;
import ar.edu.itba.paw.webapp.form.RestaurantForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
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
    public Response createRestaurant(@Valid final RestaurantForm restaurantForm) {
        byte[] image;
        try {
            image = restaurantForm.getImage().getBytes();
        } catch (IOException e) {
            throw new IllegalStateException(); // This should never happen because of @ValidImage.
        }
        Restaurant newRestaurant = rs.create(restaurantForm.getName(),
                image,
                restaurantForm.getAddress(),
                restaurantForm.getEmail(),
                restaurantForm.getDetail(),
                Zone.getByName(restaurantForm.getZone()),
                restaurantForm.getLat(),
                restaurantForm.getLng(),
                restaurantForm.getCategories(),
                restaurantForm.getShifts());
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
