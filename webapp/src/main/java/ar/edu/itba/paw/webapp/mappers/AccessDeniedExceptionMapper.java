package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.webapp.dto.ExceptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessDeniedExceptionMapper.class);

    @Override
    public Response toResponse(AccessDeniedException exception) {
        LOGGER.debug("This is happening in the AccessDeniedExceptionMapper"); // TODO: This exception is also thrown on Unauthenticated access denial. And that should be a 401, not 403
        return Response.status(Response.Status.FORBIDDEN).entity(ExceptionDTO.fromException(exception)).build();
    }
}
