package cl.company.omypay.helpcenter.feedback.infrastructure.web;

import cl.company.omypay.helpcenter.feedback.application.UserFeedbackCreateData;
import cl.company.omypay.helpcenter.feedback.application.UserFeedbackService;
import cl.company.omypay.helpcenter.shared.logging.Logger;
import cl.company.omypay.helpcenter.shared.vo.ApiResponse;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * UserFeedbackResource
 */
@Path("/api/v1/feedback")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserFeedbackResource {

    private final UserFeedbackService userFeedbackService;
    private final Logger logger;

    @Inject
    public UserFeedbackResource(UserFeedbackService userFeedbackService, Logger logger) {
        this.userFeedbackService = userFeedbackService;
        this.logger = logger;
    }

    @POST
    public Response sendFeedback(UserFeedbackRequest request) {
        try {
            userFeedbackService.sendFeedback(new UserFeedbackCreateData(request.email, request.name, request.message));
            return Response.ok(ApiResponse.ok(null)).build();
        } catch (ValidationException exception) {
            logger.warn()
                    .setMethod("sendFeedback")
                    .setDescription(exception.getMessage())
                    .setMessageParameter("user", request.email)
                    .setException(exception)
                    .log();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.failure(exception.hashCode(), exception.getMessage()))
                    .build();
        } catch (Exception exception) {
            logger.error()
                    .setMethod("sendFeedback")
                    .setDescription(exception.getMessage())
                    .setMessageParameter("user", request.email)
                    .setException(exception)
                    .log();
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(ApiResponse.failure(exception.hashCode(), exception.getMessage()))
                    .build();
        }
    }

}
