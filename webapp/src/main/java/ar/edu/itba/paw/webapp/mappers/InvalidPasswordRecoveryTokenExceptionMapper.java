package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.InvalidPasswordRecoveryTokenException;
import ar.edu.itba.paw.webapp.dto.ExceptionDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidPasswordRecoveryTokenExceptionMapper implements ExceptionMapper<InvalidPasswordRecoveryTokenException> {
    @Override
    public Response toResponse(InvalidPasswordRecoveryTokenException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(ExceptionDTO.fromException(exception)).build();
    }
}
