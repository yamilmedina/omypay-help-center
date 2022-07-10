package cl.company.omypay.helpcenter.shared.adapters.web;

import cl.company.omypay.helpcenter.shared.vo.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.lang.invoke.MethodHandles;

/**
 * InternalErrorExceptionMapper
 */
@Provider
public class InternalErrorExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Response toResponse(Exception exception) {
        LOGGER.error("Internal unhandled error occurred while trying to execute an operation", exception);
        return Response.status(Status.SERVICE_UNAVAILABLE)
                .entity(ApiResponse.failure(exception.getMessage().hashCode(), exception.getMessage()))
                .build();
    }
}