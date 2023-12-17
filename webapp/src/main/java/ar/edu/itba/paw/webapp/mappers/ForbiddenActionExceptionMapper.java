package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.ForbiddenActionException;
import ar.edu.itba.paw.webapp.dto.ExceptionDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ForbiddenActionExceptionMapper implements ExceptionMapper<ForbiddenActionException> {
    @Override
    public Response toResponse(ForbiddenActionException exception) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(ExceptionDTO.fromException(exception)).build();
    }
}