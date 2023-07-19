package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.webapp.dto.ExceptionDTO;
import org.springframework.security.access.AccessDeniedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

    @Override
    public Response toResponse(AccessDeniedException exception) {
        return Response.status(Response.Status.FORBIDDEN).entity(ExceptionDTO.fromException(exception)).build();
    }
}
