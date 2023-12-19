package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.webapp.dto.ExceptionDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(ExceptionDTO.fromException(exception)).build();
    }
}
