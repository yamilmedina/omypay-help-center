package cl.company.omypay.helpcenter.issue.infrastructure.web;

import cl.company.omypay.helpcenter.issue.application.UserIssueCreateData;
import cl.company.omypay.helpcenter.issue.application.UserIssueService;
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
 * UserIssueResource
 */
@Path("/api/v1/issue")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserIssueResource {

    private final UserIssueService userIssueService;
    private final Logger logger;

    @Inject
    public UserIssueResource(UserIssueService userIssueService, Logger logger) {
        this.userIssueService = userIssueService;
        this.logger = logger;
    }

    @POST
    public Response sendIssue(UserIssueRequest request) {
        try {
            userIssueService.sendIssue(UserIssueCreateData.of(request.email, request.name, request.message, request.category));
            return Response.ok(ApiResponse.ok(null)).build();
        } catch (ValidationException exception) {
            logger.warn()
                    .setMethod("sendIssue")
                    .setMessageParameter("user", request.email)
                    .setDescription(exception.getMessage())
                    .setException(exception)
                    .log();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.failure(exception.hashCode(), exception.getMessage()))
                    .build();
        } catch (Exception exception) {
            logger.error()
                    .setMethod("sendIssue")
                    .setMessageParameter("user", request.email)
                    .setDescription(exception.getMessage())
                    .setException(exception)
                    .log();
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(ApiResponse.failure(exception.hashCode(), exception.getMessage()))
                    .build();
        }
    }

}
